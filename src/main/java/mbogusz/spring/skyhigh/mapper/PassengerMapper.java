package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerDTO;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {PassengerRepository.class})
public abstract class PassengerMapper extends EntityMapper<Long, Passenger, PassengerDTO> {

    private PassengerRepository repository;

    @Override
    protected JpaRepository<Passenger, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Passenger> createInstance() {
        return Passenger::new;
    }

    @Autowired
    public void setRepository(PassengerRepository repository) {
        this.repository = repository;
    }
}
