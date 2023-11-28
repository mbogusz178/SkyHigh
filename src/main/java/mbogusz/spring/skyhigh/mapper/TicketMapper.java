package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.entity.dto.TicketDTO;
import mbogusz.spring.skyhigh.repository.TicketRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {TicketRepository.class})
public abstract class TicketMapper extends EntityMapper<Long, Ticket, TicketDTO> {

    private TicketRepository repository;

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
}
