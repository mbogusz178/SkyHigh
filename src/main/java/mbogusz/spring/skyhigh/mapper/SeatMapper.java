package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.FlightClass;
import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.entity.dto.SeatDTO;
import mbogusz.spring.skyhigh.repository.FlightClassRepository;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {SeatRepository.class, FlightRepository.class})
public abstract class SeatMapper extends EntityMapper<Long, Seat, SeatDTO> {

    private SeatRepository repository;
    private FlightRepository flightRepository;

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
    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Mapping(source = "flight", target = "flight", qualifiedByName = "flightToId")
    @Override
    public abstract SeatDTO toDto(Seat entity);

    @Mapping(source = "flight", target = "flight", qualifiedByName = "idToFlight")
    @Override
    public abstract Seat map(SeatDTO seatDTO, @MappingTarget Seat entity);

    @Named("flightToId")
    protected Long flightToId(Flight flight) {
        return flight.getId();
    }

    @Named("idToFlight")
    protected Flight idToFlight(Long id) {
        return flightRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
