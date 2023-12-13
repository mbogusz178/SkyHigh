package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.*;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.TicketDTO;
import mbogusz.spring.skyhigh.entity.dto.TicketReservationChangeDTO;
import mbogusz.spring.skyhigh.entity.dto.TicketReservationDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.FlightBookedMapper;
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
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketEndpoint extends BaseEndpoint<Long, Ticket, TicketDTO> {

    private final TicketRepository repository;
    private final SeatRepository seatRepository;
    private final PassengerRepository passengerRepository;
    private final FlightBookedMapper flightBookedMapper;
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
    public TicketEndpoint(TicketRepository repository, SeatRepository seatRepository, PassengerRepository passengerRepository, FlightBookedMapper flightBookedMapper, TicketMapper mapper, MailSenderService mailSenderService) {
        this.repository = repository;
        this.seatRepository = seatRepository;
        this.passengerRepository = passengerRepository;
        this.mapper = mapper;
        this.mailSenderService = mailSenderService;
        this.flightBookedMapper = flightBookedMapper;
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
        if(seat.getStatus() != SeatStatus.AVAILABLE) return new ResponseEntity<>("Miejsce jest już zajęte bądź niedostępne: " + seat.getRowNumber() + seat.getSeatLetter(), HttpStatus.BAD_REQUEST);
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
            if(seat1.getStatus() != SeatStatus.AVAILABLE) return new ResponseEntity<>("Miejsce jest już zajęte bądź niedostępne: " + seat1.getRowNumber() + seat1.getSeatLetter(), HttpStatus.BAD_REQUEST);
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

    @PutMapping("/editTicket")
    public ResponseEntity<Object> editTicket(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody TicketReservationChangeDTO changeRequest) {
        if(userDetails == null) return new ResponseEntity<>("Nie jesteś zalogowany", HttpStatus.FORBIDDEN);

        Passenger user = passengerRepository.getByEmail(userDetails.getUsername());

        Ticket ticket = repository.findById(changeRequest.getTicketId()).orElseThrow(NotFoundException::new);
        Seat oldSeat = ticket.getSeat();

        if(!Objects.equals(ticket.getPassenger().getId(), user.getId())) {
            return new ResponseEntity<>("To miejsce nie jest zarezerwowane dla Ciebie", HttpStatus.FORBIDDEN);
        }

        Seat newSeat = seatRepository.getSeatFromPosition(ticket.getSeat().getFlight().getId(), changeRequest.getRowNumber(), changeRequest.getSeatLetter());
        if(newSeat.getStatus() != SeatStatus.AVAILABLE) {
            return new ResponseEntity<>("To miejsce jest już zajęte. Wybierz inne", HttpStatus.BAD_REQUEST);
        }

        newSeat.setStatus(SeatStatus.BOOKED);
        oldSeat.setStatus(SeatStatus.AVAILABLE);
        ticket.setSeat(newSeat);
        ticket.setFirstName(changeRequest.getFirstName());
        ticket.setLastName(changeRequest.getLastName());
        seatRepository.saveAll(List.of(newSeat, oldSeat));
        Ticket newTicket = repository.save(ticket);
        return new ResponseEntity<>("Dane rezerwacji zmienione pomyślnie", HttpStatus.OK);
    }
}
