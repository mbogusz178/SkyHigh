package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.PlaneModel;
import mbogusz.spring.skyhigh.entity.dto.PlaneModelDTO;
import mbogusz.spring.skyhigh.repository.PlaneModelRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {PlaneModelRepository.class})
public abstract class PlaneModelMapper extends EntityMapper<Long, PlaneModel, PlaneModelDTO> {

    private PlaneModelRepository repository;

    @Override
    protected JpaRepository<PlaneModel, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<PlaneModel> createInstance() {
        return PlaneModel::new;
    }

    @Autowired
    public void setRepository(PlaneModelRepository repository) {
        this.repository = repository;
    }
}
