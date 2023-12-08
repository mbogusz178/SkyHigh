package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.FlightBookingDataDTO;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.entity.dto.FlightSearchResponseDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.FlightBookingDataMapper;
import mbogusz.spring.skyhigh.mapper.FlightMapper;
import mbogusz.spring.skyhigh.mapper.FlightSearchResponseMapper;
import mbogusz.spring.skyhigh.mapper.context.PassengerComposition;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import mbogusz.spring.skyhigh.service.TotalFlightPriceFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
public class FlightEndpoint extends BaseEndpoint<Long, Flight, FlightDTO> {

    private final FlightRepository repository;
    private final FlightMapper mapper;
    private final FlightSearchResponseMapper searchResponseMapper;
    private final FlightBookingDataMapper flightBookingDataMapper;
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
    public FlightEndpoint(FlightRepository repository, FlightMapper mapper, FlightSearchResponseMapper searchResponseMapper, FlightBookingDataMapper flightBookingDataMapper, TotalFlightPriceFilterService priceFilterService) {
        this.repository = repository;
        this.mapper = mapper;
        this.searchResponseMapper = searchResponseMapper;
        this.flightBookingDataMapper = flightBookingDataMapper;
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
}
