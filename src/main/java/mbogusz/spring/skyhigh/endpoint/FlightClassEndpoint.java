package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.FlightClass;
import mbogusz.spring.skyhigh.entity.dto.FlightClassDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.FlightClassMapper;
import mbogusz.spring.skyhigh.repository.FlightClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flightClasses")
public class FlightClassEndpoint extends BaseEndpoint<Long, FlightClass, FlightClassDTO> {

    private final FlightClassRepository repository;
    private final FlightClassMapper mapper;

    @Override
    public EntityMapper<Long, FlightClass, FlightClassDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<FlightClass, Long> getRepository() {
        return repository;
    }

    @Autowired
    public FlightClassEndpoint(FlightClassRepository repository, FlightClassMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }}
