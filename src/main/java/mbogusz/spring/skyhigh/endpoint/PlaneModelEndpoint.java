package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.PlaneModel;
import mbogusz.spring.skyhigh.entity.dto.PlaneModelDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.PlaneModelMapper;
import mbogusz.spring.skyhigh.repository.PlaneModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planeModels")
public class PlaneModelEndpoint extends BaseEndpoint<Long, PlaneModel, PlaneModelDTO> {

    private final PlaneModelRepository repository;
    private final PlaneModelMapper mapper;

    @Override
    public EntityMapper<Long, PlaneModel, PlaneModelDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<PlaneModel, Long> getRepository() {
        return repository;
    }

    @Autowired
    public PlaneModelEndpoint(PlaneModelRepository repository, PlaneModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
