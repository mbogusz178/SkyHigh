package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.SeatConfiguration;
import mbogusz.spring.skyhigh.entity.dto.SeatConfigurationDTO;
import mbogusz.spring.skyhigh.repository.SeatConfigurationRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {SeatConfigurationRepository.class})
public abstract class SeatConfigurationMapper extends EntityMapper<Long, SeatConfiguration, SeatConfigurationDTO> {

    private SeatConfigurationRepository repository;

    @Override
    protected JpaRepository<SeatConfiguration, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<SeatConfiguration> createInstance() {
        return SeatConfiguration::new;
    }

    @Autowired
    public void setRepository(SeatConfigurationRepository repository) {
        this.repository = repository;
    }
}
