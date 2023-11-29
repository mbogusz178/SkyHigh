package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.SeatConfiguration;
import mbogusz.spring.skyhigh.entity.dto.SeatConfigurationDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.SeatConfigurationMapper;
import mbogusz.spring.skyhigh.repository.SeatConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seatConfigurations")
public class SeatConfigurationEndpoint extends BaseEndpoint<Long, SeatConfiguration, SeatConfigurationDTO> {

    private final SeatConfigurationRepository repository;
    private final SeatConfigurationMapper mapper;

    @Override
    public EntityMapper<Long, SeatConfiguration, SeatConfigurationDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<SeatConfiguration, Long> getRepository() {
        return repository;
    }

    @Autowired
    public SeatConfigurationEndpoint(SeatConfigurationRepository repository, SeatConfigurationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
