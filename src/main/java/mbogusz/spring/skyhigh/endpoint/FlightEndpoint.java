package mbogusz.spring.skyhigh.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.SeatStatus;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
@Tag(name = "Flights")
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

    @Operation(summary = "Search available flights", description = "Returns flights that match specified criteria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flights retrieved"),
    })
    @GetMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDTO>> search(@RequestParam(required = false) @Parameter(name = "source", description = "Name of the city being the start of the flight", example = "New York") String source,
                                                                @RequestParam(required = false) @Parameter(name = "destination", description = "Name of the city being the end of the flight", example = "Los Angeles") String destination,
                                                                @RequestParam(required = false) @Parameter(name = "departureAfter", description = "Earliest departure date, formatted as yyyy-MM-ddTHH:mm:ssZ in UTC timezone", example = "2023-12-11T07:22:00Z") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date departureAfter,
                                                                @RequestParam(required = false) @Parameter(name = "departureBefore", description = "Latest departure date, formatted as yyyy-MM-ddTHH:mm:ssZ in UTC timezone", example = "2023-12-11T07:22:00Z") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date departureBefore,
                                                                @RequestParam(required = false) @Parameter(name = "arrivalAfter", description = "Earliest arrival date, formatted as yyyy-MM-ddTHH:mm:ssZ in UTC timezone", example = "2023-12-11T07:22:00Z") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date arrivalAfter,
                                                                @RequestParam(required = false) @Parameter(name = "arrivalBefore", description = "Latest arrival date, formatted as yyyy-MM-ddTHH:mm:ssZ in UTC timezone", example = "2023-12-11T07:22:00Z") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date arrivalBefore,
                                                                @RequestParam @Parameter(name = "adultCount", description = "Number of adults booking a seat", example = "2") Integer adultCount,
                                                                @RequestParam @Parameter(name = "childCount", description = "Number of children booking a seat", example = "2") Integer childCount,
                                                                @RequestParam(required = false) @Parameter(name = "flightTicketMinPrice", description = "Minimum price of the entire flight, calculated from tickets for all adults and children") Double flightTicketMinPrice,
                                                                @RequestParam(required = false) @Parameter(name = "flightTicketMaxPrice", description = "Maximum price of the entire flight, calculated from tickets for all adults and children") Double flightTicketMaxPrice) {
        Timestamp departureAfterTimestamp = departureAfter != null ? new Timestamp(departureAfter.getTime()) : null;
        Timestamp departureBeforeTimestamp = departureBefore != null ? new Timestamp(departureBefore.getTime()) : null;
        Timestamp arrivalBeforeTimestamp = arrivalBefore != null ? new Timestamp(arrivalBefore.getTime()) : null;
        Timestamp arrivalAfterTimestamp = arrivalAfter != null ? new Timestamp(arrivalAfter.getTime()) : null;
        List<Flight> flights = repository.searchFlights(source, destination, departureAfterTimestamp, departureBeforeTimestamp, arrivalAfterTimestamp, arrivalBeforeTimestamp);
        List<Flight> finalFlights = priceFilterService.filterTotalFlightPrice(flights, adultCount, childCount, flightTicketMinPrice, flightTicketMaxPrice);
        List<FlightSearchResponseDTO> flightDTOs = finalFlights.stream().map(flight -> searchResponseMapper.toDto(flight, new PassengerComposition(adultCount, childCount))).collect(Collectors.toList());
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get data used when booking a flight", description = "Returns data about the plane used in the flight")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data retrieved"),
            @ApiResponse(responseCode = "404", description = "No flight with specified ID found")
    })
    @GetMapping("/{id}/bookingData")
    public ResponseEntity<FlightBookingDataDTO> bookingData(@PathVariable @Parameter(name = "id", description = "Flight ID", required = true) Long id) {
        return new ResponseEntity<>(flightBookingDataMapper.toDto(repository.findById(id).orElseThrow(NotFoundException::new)), HttpStatus.OK);
    }

    @Operation(summary = "Get booked flights from logged in user", description = "Returns all flights booked by the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booked flights retrieved successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FlightBookedDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Not logged in", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/bookedFlights")
    public ResponseEntity<Object> bookedFlights(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null) return new ResponseEntity<>("Nie jesteś zalogowany", HttpStatus.FORBIDDEN);

        Passenger passenger = passengerRepository.getByEmail(userDetails.getUsername());

        List<Long> userFlightIds = repository.getFlightIdsOfUser(passenger.getId());
        List<Flight> userFlights = userFlightIds.stream().map(id -> repository.findById(id).orElseThrow(InternalServerErrorException::new)).collect(Collectors.toList());
        List<FlightBookedDTO> userFlightDTOs = userFlights.stream().map(flight -> flightBookedMapper.toDto(flight, passenger)).collect(Collectors.toList());

        for(FlightBookedDTO userFlightDTO: userFlightDTOs) {
            List<Ticket> userFlightTickets = ticketRepository.getTicketsOfUserForFlight(userFlightDTO.getId(), passenger.getId());
            List<FlightOtherPassengerDTO> otherPassengerDTOs = userFlightTickets.stream().map(flightOtherPassengerMapper::toDto).collect(Collectors.toList());
            userFlightDTO.setPassengers(otherPassengerDTOs);
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
                if(ticketStatus == TicketStatus.CANCELLED) {
                    ticket.getSeat().setStatus(SeatStatus.AVAILABLE);
                }
            }

            ticketRepository.saveAll(allFlightTicketsOfUser);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Cancel flight", description = "Cancel a flight")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight cancelled successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Not logged in", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found or already cancelled", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @Transactional
    @PostMapping(value = "/cancelFlight")
    public ResponseEntity<String> cancelFlight(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody @Parameter(name = "flightId", description = "Flight ID", examples = @ExampleObject("{\"value\": 1}")) SimpleDTO<Long> flightId) {
        return updateTicketStatus(userDetails, flightId.getValue(), TicketStatus.CANCELLED, "Pomyślnie odwołano lot", "Anulowanie lotu nie powiodło się. Spróbuj ponownie za jakiś czas");
    }

    @Operation(summary = "Confirm a flight", description = "Confirm a flight")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight confirmed successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Not logged in", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found or already confirmed", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "More than 30 minutes remaining before departure", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Transactional
    @PostMapping(value = "/confirmFlight")
    public ResponseEntity<String> confirmFlight(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody @Parameter(name = "flightId", description = "Flight ID", examples = @ExampleObject("{\"value\": 1}")) SimpleDTO<Long> flightId) {
        final long MINUTE_MILLIS = 60000;

        try {
            Flight flight = repository.findById(flightId.getValue()).orElseThrow();
            Timestamp departureDate = flight.getDepartureDate();
            if(new Date(departureDate.getTime() - (30 * MINUTE_MILLIS)).after(new Date())) { // if more than 30 minutes before flight
                return new ResponseEntity<>("Można potwierdzić lot tylko 30 minut przed odlotem", HttpStatus.BAD_REQUEST);
            }
            return updateTicketStatus(userDetails, flightId.getValue(), TicketStatus.CONFIRMED, "Pomyślnie potwierdzono lot", "Potwierdzenie lotu nie powiodło się. Spróbuj ponownie za jakiś czas");
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Nie znaleziono lotu o podanym ID", HttpStatus.NOT_FOUND);
        }
    }
}
