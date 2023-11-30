package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.PassengerMapper;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passengers")
public class PassengerEndpoint extends BaseEndpoint<Long, Passenger, PassengerDTO> {

    private final PassengerRepository repository;
    private final PassengerMapper mapper;

    @Override
    public EntityMapper<Long, Passenger, PassengerDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Passenger, Long> getRepository() {
        return repository;
    }

    @Autowired
    public PassengerEndpoint(PassengerRepository repository, PassengerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
