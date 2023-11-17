package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.SeatConfiguration;
import mbogusz.spring.skyhigh.repository.SeatConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seatConfigurations")
public class SeatConfigurationEndpoint extends BaseEndpoint<Long, SeatConfiguration> {

    private final SeatConfigurationRepository repository;
    @Override
    public JpaRepository<SeatConfiguration, Long> getRepository() {
        return repository;
    }

    @Autowired
    public SeatConfigurationEndpoint(SeatConfigurationRepository repository) {
        this.repository = repository;
    }
}
