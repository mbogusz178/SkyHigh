package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.entity.dto.SeatDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.SeatMapper;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seats")
public class SeatEndpoint extends BaseEndpoint<Long, Seat, SeatDTO> {

    private final SeatRepository repository;
    private final SeatMapper mapper;

    @Override
    public EntityMapper<Long, Seat, SeatDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Seat, Long> getRepository() {
        return repository;
    }

    @Autowired
    public SeatEndpoint(SeatRepository repository, SeatMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
