package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.dto.AirportDTO;
import mbogusz.spring.skyhigh.mapper.AirportMapper;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airports")
public class AirportEndpoint extends BaseEndpoint<String, Airport, AirportDTO> {

    private final AirportRepository repository;
    private final AirportMapper mapper;

    @Override
    public EntityMapper<String, Airport, AirportDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Airport, String> getRepository() {
        return repository;
    }

    @Autowired
    public AirportEndpoint(AirportRepository repository, AirportMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
