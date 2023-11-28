package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.dto.AirportDTO;
import mbogusz.spring.skyhigh.repository.AirportRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {AirportRepository.class})
public abstract class AirportMapper extends EntityMapper<String, Airport, AirportDTO> {

    private AirportRepository repository;

    @Override
    protected JpaRepository<Airport, String> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Airport> createInstance() {
        return Airport::new;
    }

    @Autowired
    public void setRepository(AirportRepository repository) {
        this.repository = repository;
    }
}
