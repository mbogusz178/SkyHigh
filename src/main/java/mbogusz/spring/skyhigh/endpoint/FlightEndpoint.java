package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.FlightMapper;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
public class FlightEndpoint extends BaseEndpoint<Long, Flight, FlightDTO> {

    private final FlightRepository repository;
    private final FlightMapper mapper;

    @Override
    public EntityMapper<Long, Flight, FlightDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Flight, Long> getRepository() {
        return repository;
    }

    @Autowired
    public FlightEndpoint(FlightRepository repository, FlightMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightDTO>> search(@RequestParam(required = false) String source,
                                                  @RequestParam(required = false) String destination,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date departureAfter,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date departureBefore,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date arrivalAfter,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date arrivalBefore,
                                                  @RequestParam(required = false) Double adultTicketMinPrice,
                                                  @RequestParam(required = false) Double adultTicketMaxPrice,
                                                  @RequestParam(required = false) Double childTicketMinPrice,
                                                  @RequestParam(required = false) Double childTicketMaxPrice) {
        Timestamp departureAfterTimestamp = departureAfter != null ? new Timestamp(departureAfter.getTime()) : null;
        Timestamp departureBeforeTimestamp = departureBefore != null ? new Timestamp(departureBefore.getTime()) : null;
        Timestamp arrivalBeforeTimestamp = arrivalBefore != null ? new Timestamp(arrivalBefore.getTime()) : null;
        Timestamp arrivalAfterTimestamp = arrivalAfter != null ? new Timestamp(arrivalAfter.getTime()) : null;
        List<Flight> flights = repository.searchFlights(source, destination, departureAfterTimestamp, departureBeforeTimestamp, arrivalAfterTimestamp, arrivalBeforeTimestamp, adultTicketMinPrice, adultTicketMaxPrice, childTicketMinPrice, childTicketMaxPrice);
        List<FlightDTO> flightDTOs = flights.stream().map(mapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(flightDTOs, HttpStatus.OK);
    }
}
