package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.entity.TicketStatus;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.*;
import mbogusz.spring.skyhigh.entity.dto.template.SimpleDTO;
import mbogusz.spring.skyhigh.mapper.*;
import mbogusz.spring.skyhigh.mapper.context.PassengerComposition;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import mbogusz.spring.skyhigh.repository.TicketRepository;
import mbogusz.spring.skyhigh.service.TotalFlightPriceFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
public class FlightEndpoint extends BaseEndpoint<Long, Flight, FlightDTO> {

    private final FlightRepository repository;
    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final FlightMapper mapper;
    private final FlightSearchResponseMapper searchResponseMapper;
    private final FlightBookingDataMapper flightBookingDataMapper;
    private final FlightBookedMapper flightBookedMapper;
    private final FlightOtherPassengerMapper flightOtherPassengerMapper;
    private final TotalFlightPriceFilterService priceFilterService;

    @Override
    public EntityMapper<Long, Flight, FlightDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Flight, Long> getRepository() {
        return repository;
    }

    @Autowired
    public FlightEndpoint(FlightRepository repository, PassengerRepository passengerRepository, TicketRepository ticketRepository, FlightMapper mapper, FlightSearchResponseMapper searchResponseMapper, FlightBookingDataMapper flightBookingDataMapper, FlightBookedMapper flightBookedMapper, FlightOtherPassengerMapper flightOtherPassengerMapper, TotalFlightPriceFilterService priceFilterService) {
        this.repository = repository;
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
        this.searchResponseMapper = searchResponseMapper;
        this.flightBookingDataMapper = flightBookingDataMapper;
        this.flightBookedMapper = flightBookedMapper;
        this.flightOtherPassengerMapper = flightOtherPassengerMapper;
        this.priceFilterService = priceFilterService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDTO>> search(@RequestParam(required = false) String source,
                                                                @RequestParam(required = false) String destination,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date departureAfter,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date departureBefore,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date arrivalAfter,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date arrivalBefore,
                                                                @RequestParam Integer adultCount,
                                                                @RequestParam Integer childCount,
                                                                @RequestParam(required = false) Double flightTicketMinPrice,
                                                                @RequestParam(required = false) Double flightTicketMaxPrice) {
        Timestamp departureAfterTimestamp = departureAfter != null ? new Timestamp(departureAfter.getTime()) : null;
        Timestamp departureBeforeTimestamp = departureBefore != null ? new Timestamp(departureBefore.getTime()) : null;
        Timestamp arrivalBeforeTimestamp = arrivalBefore != null ? new Timestamp(arrivalBefore.getTime()) : null;
        Timestamp arrivalAfterTimestamp = arrivalAfter != null ? new Timestamp(arrivalAfter.getTime()) : null;
        List<Flight> flights = repository.searchFlights(source, destination, departureAfterTimestamp, departureBeforeTimestamp, arrivalAfterTimestamp, arrivalBeforeTimestamp);
        List<Flight> finalFlights = priceFilterService.filterTotalFlightPrice(flights, adultCount, childCount, flightTicketMinPrice, flightTicketMaxPrice);
        List<FlightSearchResponseDTO> flightDTOs = finalFlights.stream().map(flight -> searchResponseMapper.toDto(flight, new PassengerComposition(adultCount, childCount))).collect(Collectors.toList());
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}/bookingData")
    public ResponseEntity<FlightBookingDataDTO> bookingData(@PathVariable Long id) {
        return new ResponseEntity<>(flightBookingDataMapper.toDto(repository.findById(id).orElseThrow(NotFoundException::new)), HttpStatus.OK);
    }

    @GetMapping("/bookedFlights")
    public ResponseEntity<Object> bookedFlights(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null) return new ResponseEntity<>("Nie jesteś zalogowany", HttpStatus.FORBIDDEN);

        Passenger passenger = passengerRepository.getByEmail(userDetails.getUsername());

        List<Long> userFlightIds = repository.getFlightIdsOfUser(passenger.getId());
        List<Flight> userFlights = userFlightIds.stream().map(id -> repository.findById(id).orElseThrow(InternalServerErrorException::new)).collect(Collectors.toList());
        List<FlightBookedDTO> userFlightDTOs = userFlights.stream().map(flightBookedMapper::toDto).collect(Collectors.toList());

        for(FlightBookedDTO userFlightDTO: userFlightDTOs) {
            List<Ticket> userFlightTickets = ticketRepository.getTicketsOfUserForFlight(userFlightDTO.getId(), passenger.getId());
            List<FlightOtherPassengerDTO> otherPassengerDTOs = userFlightTickets.stream().map(flightOtherPassengerMapper::toDto).collect(Collectors.toList());
            userFlightDTO.setOtherPassengers(otherPassengerDTOs);
        }

        return new ResponseEntity<>(userFlightDTOs, HttpStatus.OK);
    }

    private ResponseEntity<String> updateTicketStatus(UserDetails userDetails, Long flightId, TicketStatus ticketStatus, String successMessage, String errorMessage) {
        if(userDetails == null) return new ResponseEntity<>("Nie jesteś zalogowany", HttpStatus.FORBIDDEN);

        Passenger passenger = passengerRepository.getByEmail(userDetails.getUsername());

        try {
            List<Ticket> allFlightTicketsOfUser = ticketRepository.getTicketsOfUserForFlight(flightId, passenger.getId());
            if(allFlightTicketsOfUser.isEmpty()) return new ResponseEntity<>("Nie zarezerwowałeś tego lotu bądź został on już odwołany", HttpStatus.NOT_FOUND);
            for (Ticket ticket: allFlightTicketsOfUser) {
                ticket.setStatus(ticketStatus);
            }
            ticketRepository.saveAll(allFlightTicketsOfUser);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cancelFlight")
    public ResponseEntity<String> cancelFlight(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody SimpleDTO<Long> flightId) {
        return updateTicketStatus(userDetails, flightId.getValue(), TicketStatus.CANCELLED, "Pomyślnie odwołano lot", "Anulowanie lotu nie powiodło się. Spróbuj ponownie za jakiś czas");
    }

    @PostMapping(value = "/confirmFlight")
    public ResponseEntity<String> confirmFlight(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody SimpleDTO<Long> flightId) {
        return updateTicketStatus(userDetails, flightId.getValue(), TicketStatus.CONFIRMED, "Pomyślnie potwierdzono lot", "Potwierdzenie lotu nie powiodło się. Spróbuj ponownie za jakiś czas");
    }
}
