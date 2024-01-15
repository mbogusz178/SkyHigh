package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.repository.AirportRepository;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {FlightRepository.class, AirportRepository.class, PlaneRepository.class})
public abstract class FlightMapper extends EntityMapper<Long, Flight, FlightDTO> {

    private FlightRepository repository;
    private AirportRepository airportRepository;
    private PlaneRepository planeRepository;

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

    @Autowired
    public void setPlaneRepository(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "airportToId"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "airportToId"),
            @Mapping(source = "plane", target = "plane", qualifiedByName = "planeToId"),
            @Mapping(source = "departureDate", target = "departureDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"),
            @Mapping(source = "arrivalDate", target = "arrivalDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    })
    @Override
    public abstract FlightDTO toDto(Flight entity);

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "idToAirport"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "idToAirport"),
            @Mapping(source = "plane", target = "plane", qualifiedByName = "idToPlane")
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

    @Named("planeToId")
    protected String planeToId(Plane plane) {
        return plane.getId();
    }

    @Named("idToPlane")
    protected Plane idToPlane(String id) {
        return planeRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
