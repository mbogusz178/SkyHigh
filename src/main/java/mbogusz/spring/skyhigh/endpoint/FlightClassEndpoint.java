package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.FlightClass;
import mbogusz.spring.skyhigh.repository.FlightClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flightClasses")
public class FlightClassEndpoint extends BaseEndpoint<Long, FlightClass> {

    private final FlightClassRepository repository;
    @Override
    public JpaRepository<FlightClass, Long> getRepository() {
        return repository;
    }

    @Autowired
    public FlightClassEndpoint(FlightClassRepository repository) {
        this.repository = repository;
    }
}
