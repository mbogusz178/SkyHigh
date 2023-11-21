package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.FlightClass;
import mbogusz.spring.skyhigh.entity.dto.FlightClassDTO;
import mbogusz.spring.skyhigh.repository.FlightClassRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {FlightClassRepository.class})
public abstract class FlightClassMapper extends EntityMapper<Long, FlightClass, FlightClassDTO> {

    private final FlightClassRepository repository;

    @Override
    protected JpaRepository<FlightClass, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<FlightClass> createInstance() {
        return FlightClass::new;
    }

    @Autowired
    public FlightClassMapper(FlightClassRepository repository) {
        this.repository = repository;
    }
}
