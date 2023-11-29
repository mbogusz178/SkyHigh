package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.repository.AirportRepository;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {FlightRepository.class, AirportRepository.class})
public abstract class FlightMapper extends EntityMapper<Long, Flight, FlightDTO> {

    private FlightRepository repository;
    private AirportRepository airportRepository;

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

    @Autowired
    public void setAirportRepository(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "airportToId"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "airportToId")
    })
    @Override
    public abstract FlightDTO toDto(Flight entity);

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "idToAirport"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "idToAirport")
    })
    @Override
    public abstract Flight map(FlightDTO flightDTO, @MappingTarget Flight entity);

    @Named("airportToId")
    protected String airportToId(Airport airport) {
        return airport.getId();
    }

    @Named("idToAirport")
    protected Airport idToAirport(String id) {
        return airportRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
