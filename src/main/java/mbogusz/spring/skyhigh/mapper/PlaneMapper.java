package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.dto.PlaneDTO;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {PlaneRepository.class})
public abstract class PlaneMapper extends EntityMapper<String, Plane, PlaneDTO> {

    private final PlaneRepository repository;

    @Override
    protected JpaRepository<Plane, String> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Plane> createInstance() {
        return Plane::new;
    }

    @Autowired
    public PlaneMapper(PlaneRepository repository) {
        this.repository = repository;
    }
}
