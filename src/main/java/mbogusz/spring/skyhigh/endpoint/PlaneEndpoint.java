package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.dto.PlaneDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.PlaneMapper;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planes")
public class PlaneEndpoint extends BaseEndpoint<String, Plane, PlaneDTO> {

    private final PlaneRepository repository;
    private final PlaneMapper mapper;

    @Override
    public EntityMapper<String, Plane, PlaneDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Plane, String> getRepository() {
        return repository;
    }

    @Autowired
    public PlaneEndpoint(PlaneRepository repository, PlaneMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
