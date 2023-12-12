package mbogusz.spring.skyhigh.service;

import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@EnableScheduling
public class ConfirmationNotifier {

    private final TicketRepository ticketRepository;
    private final MailSenderService mailSenderService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void sendConfirmationEmail() {
        System.out.println("Searching tickets to notify");
        List<Ticket> eligibleTickets = ticketRepository.getTicketsForNotification();
        if(eligibleTickets.isEmpty()) System.out.println("No tickets found");
        for(Ticket ticket: eligibleTickets) {
            if(mailSenderService.sendConfirmationNotification(ticket)) {
                ticket.setNotified(true);
                ticketRepository.save(ticket);
            }
        }
    }

    @Autowired
    public ConfirmationNotifier(TicketRepository ticketRepository, MailSenderService mailSenderService) {
        this.ticketRepository = ticketRepository;
        this.mailSenderService = mailSenderService;
    }
}
