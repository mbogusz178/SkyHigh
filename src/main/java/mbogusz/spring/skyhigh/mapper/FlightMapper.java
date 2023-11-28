package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {FlightRepository.class})
public abstract class FlightMapper extends EntityMapper<Long, Flight, FlightDTO> {

    private FlightRepository repository;

    @Override
    protected JpaRepository<Flight, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Flight> createInstance() {
        return Flight::new;
    }

    @Autowired
    public void setRepository(FlightRepository repository) {
        this.repository = repository;
    }
}
