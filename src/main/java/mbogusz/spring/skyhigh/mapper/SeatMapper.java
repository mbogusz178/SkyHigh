package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.entity.dto.SeatDTO;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {SeatRepository.class})
public abstract class SeatMapper extends EntityMapper<Long, Seat, SeatDTO> {

    private SeatRepository repository;

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
}
