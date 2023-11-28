package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.FlightMapper;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
