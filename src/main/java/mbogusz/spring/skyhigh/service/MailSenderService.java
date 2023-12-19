package mbogusz.spring.skyhigh.service;

import lombok.RequiredArgsConstructor;
import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    public void sendFlightBookedMessage(List<Ticket> tickets, Passenger recipient, Airport source, Airport destination) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        Map<String, Object> properties = new HashMap<>();
        properties.put("recipient", recipient);
        properties.put("tickets", tickets);
        properties.put("source", source);
        properties.put("destination", destination);
        context.setVariables(properties);
        helper.setFrom(from);
        helper.setTo(recipient.getUsername());
        helper.setSubject("Flight reservation");
        String html = templateEngine.process("flight-booked-email.html", context);
        helper.setText(html, true);

        mailSender.send(message);
    }

    public boolean sendConfirmationNotification(Ticket ticket) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            Map<String, Object> properties = new HashMap<>();
            properties.put("ticket", ticket);
            context.setVariables(properties);
            helper.setFrom(from);
            helper.setTo(ticket.getPassenger().getEmail());
            helper.setSubject("Reservation confirmation");
            String html = templateEngine.process("confirmation-notification.html", context);
            helper.setText(html, true);

            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            System.out.println("Could not send email for ticket id " + ticket.getId());
            return false;
        }
    }
}
