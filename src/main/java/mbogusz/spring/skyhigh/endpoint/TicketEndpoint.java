package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.*;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.TicketDTO;
import mbogusz.spring.skyhigh.entity.dto.TicketReservationDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.TicketMapper;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import mbogusz.spring.skyhigh.repository.TicketRepository;
import mbogusz.spring.skyhigh.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketEndpoint extends BaseEndpoint<Long, Ticket, TicketDTO> {

    private final TicketRepository repository;
    private final SeatRepository seatRepository;
    private final PassengerRepository passengerRepository;
    private final TicketMapper mapper;
    private final MailSenderService mailSenderService;

    @Override
    public EntityMapper<Long, Ticket, TicketDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Ticket, Long> getRepository() {
        return repository;
    }

    @Autowired
    public TicketEndpoint(TicketRepository repository, SeatRepository seatRepository, PassengerRepository passengerRepository, TicketMapper mapper, MailSenderService mailSenderService) {
        this.repository = repository;
        this.seatRepository = seatRepository;
        this.passengerRepository = passengerRepository;
        this.mapper = mapper;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/bookTickets")
    public ResponseEntity<Object> bookTicket(@Valid @RequestBody TicketReservationDTO ticketReservationDTO, @AuthenticationPrincipal UserDetails userDetails) throws MessagingException {
        if(userDetails == null) return new ResponseEntity<>("Zaloguj się, aby zarezerwować lot", HttpStatus.FORBIDDEN);
        Passenger bookingUser = passengerRepository.getByEmail(userDetails.getUsername());

        List<Ticket> tickets = new ArrayList<>();

        TicketReservationDTO.TicketMainReservationDTO mainReservationDTO = ticketReservationDTO.getMainReservation();
        Ticket mainTicket = new Ticket();
        mainTicket.setAgeGroup(AgeGroup.ADULT);
        mainTicket.setDateBooked(new Timestamp(new Date().getTime()));
        Seat seat = seatRepository.getSeatFromPosition(ticketReservationDTO.getFlightId(), mainReservationDTO.getRowNumber(), mainReservationDTO.getSeatLetter());
        mainTicket.setSeat(seat);
        seat.setStatus(SeatStatus.BOOKED);
        mainTicket.setPassenger(bookingUser);
        mainTicket.setStatus(TicketStatus.PENDING);
        mainTicket.setFirstName(mainReservationDTO.getFirstName());
        mainTicket.setLastName(mainReservationDTO.getLastName());
        mainTicket.setFlightClass(null);
        mainTicket.setNotified(false);
        tickets.add(mainTicket);

        Flight theFlight = seat.getFlight();

        for(TicketReservationDTO.TicketOtherReservationsDTO otherReservationsDTO: ticketReservationDTO.getOtherPassengers()) {
            Ticket otherTicket = new Ticket();
            otherTicket.setAgeGroup(AgeGroup.valueOf(otherReservationsDTO.getAgeGroup()));
            otherTicket.setFlightClass(null);
            Seat seat1 = seatRepository.getSeatFromPosition(ticketReservationDTO.getFlightId(), otherReservationsDTO.getRowNumber(), otherReservationsDTO.getSeatLetter());
            otherTicket.setSeat(seat1);
            seat1.setStatus(SeatStatus.BOOKED);
            otherTicket.setPassenger(bookingUser);
            otherTicket.setStatus(TicketStatus.PENDING);
            otherTicket.setFirstName(otherReservationsDTO.getFirstName());
            otherTicket.setLastName(otherReservationsDTO.getLastName());
            otherTicket.setDateBooked(new Timestamp(new Date().getTime()));
            otherTicket.setNotified(false);
            tickets.add(otherTicket);
        }

        List<Ticket> savedTickets = repository.saveAll(tickets);
        mailSenderService.sendFlightBookedMessage(savedTickets, bookingUser, theFlight.getSource(), theFlight.getDestination());
        return new ResponseEntity<>(savedTickets.stream().map(mapper::toDto).collect(Collectors.toList()), HttpStatus.CREATED);
    }
}
