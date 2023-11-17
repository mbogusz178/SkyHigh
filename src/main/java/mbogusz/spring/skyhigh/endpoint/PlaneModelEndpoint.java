package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.PlaneModel;
import mbogusz.spring.skyhigh.repository.PlaneModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planeModels")
public class PlaneModelEndpoint extends BaseEndpoint<Long, PlaneModel> {

    private final PlaneModelRepository repository;
    @Override
    public JpaRepository<PlaneModel, Long> getRepository() {
        return repository;
    }

    @Autowired
    public PlaneModelEndpoint(PlaneModelRepository repository) {
        this.repository = repository;
    }
}
