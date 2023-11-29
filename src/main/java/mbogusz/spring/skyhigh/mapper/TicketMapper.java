package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.TicketDTO;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import mbogusz.spring.skyhigh.repository.TicketRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {TicketRepository.class, PassengerRepository.class, SeatRepository.class})
public abstract class TicketMapper extends EntityMapper<Long, Ticket, TicketDTO> {

    private TicketRepository repository;
    private PassengerRepository passengerRepository;
    private SeatRepository seatRepository;

    @Override
    protected JpaRepository<Ticket, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Ticket> createInstance() {
        return Ticket::new;
    }

    @Autowired
    public void setRepository(TicketRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPassengerRepository(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Autowired
    public void setSeatRepository(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Mappings({
            @Mapping(source = "passenger", target = "passenger", qualifiedByName = "passengerToId"),
            @Mapping(source = "seat", target = "seat", qualifiedByName = "seatToId")
    })
    @Override
    public abstract TicketDTO toDto(Ticket entity);

    @Mappings({
            @Mapping(source = "passenger", target = "passenger", qualifiedByName = "idToPassenger"),
            @Mapping(source = "seat", target = "seat", qualifiedByName = "idToSeat")
    })
    @Override
    public abstract Ticket map(TicketDTO ticketDTO, @MappingTarget Ticket entity);

    @Named("passengerToId")
    protected Long passengerToId(Passenger passenger) {
        return passenger.getId();
    }

    @Named("idToPassenger")
    protected Passenger idToPassenger(Long id) {
        return passengerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Named("seatToId")
    protected Long seatToId(Seat seat) {
        return seat.getId();
    }

    @Named("idToSeat")
    protected Seat idToSeat(Long id) {
        return seatRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
