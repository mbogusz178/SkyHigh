package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planes")
public class PlaneEndpoint extends BaseEndpoint<String, Plane> {

    private final PlaneRepository repository;

    @Override
    public JpaRepository<Plane, String> getRepository() {
        return repository;
    }

    @Autowired
    public PlaneEndpoint(PlaneRepository repository) {
        this.repository = repository;
    }
}
