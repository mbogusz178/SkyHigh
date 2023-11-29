package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.FlightClass;
import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.entity.dto.SeatDTO;
import mbogusz.spring.skyhigh.repository.FlightClassRepository;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {SeatRepository.class, PlaneRepository.class, FlightClassRepository.class})
public abstract class SeatMapper extends EntityMapper<Long, Seat, SeatDTO> {

    private SeatRepository repository;
    private PlaneRepository planeRepository;
    private FlightClassRepository flightClassRepository;

    @Override
    protected JpaRepository<Seat, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Seat> createInstance() {
        return Seat::new;
    }

    @Autowired
    public void setRepository(SeatRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPlaneRepository(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Autowired
    public void setFlightClassRepository(FlightClassRepository flightClassRepository) {
        this.flightClassRepository = flightClassRepository;
    }

    @Mappings({
            @Mapping(source = "plane", target = "plane", qualifiedByName = "planeToId"),
            @Mapping(source = "flightClass", target = "flightClass", qualifiedByName = "flightClassToId")
    })
    @Override
    public abstract SeatDTO toDto(Seat entity);

    @Mappings({
            @Mapping(source = "plane", target = "plane", qualifiedByName = "idToPlane"),
            @Mapping(source = "flightClass", target = "flightClass", qualifiedByName = "idToFlightClass")
    })
    @Override
    public abstract Seat map(SeatDTO seatDTO, @MappingTarget Seat entity);

    @Named("planeToId")
    protected String planeToId(Plane plane) {
        return plane.getId();
    }

    @Named("idToPlane")
    protected Plane idToPlane(String id) {
        return planeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Named("flightClassToId")
    protected Long flightClassToId(FlightClass flightClass) {
        return flightClass.getId();
    }

    @Named("idToFlightClass")
    protected FlightClass idToFlightClass(Long id) {
        return flightClassRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
