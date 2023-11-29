package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.entity.dto.TicketDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.TicketMapper;
import mbogusz.spring.skyhigh.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketEndpoint extends BaseEndpoint<Long, Ticket, TicketDTO> {

    private final TicketRepository repository;
    private final TicketMapper mapper;

    @Override
    public EntityMapper<Long, Ticket, TicketDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Ticket, Long> getRepository() {
        return repository;
    }

    @Autowired
    public TicketEndpoint(TicketRepository repository, TicketMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
