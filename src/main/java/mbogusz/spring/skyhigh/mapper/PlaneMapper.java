package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.PlaneModel;
import mbogusz.spring.skyhigh.entity.SeatConfiguration;
import mbogusz.spring.skyhigh.entity.dto.PlaneDTO;
import mbogusz.spring.skyhigh.repository.PlaneModelRepository;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import mbogusz.spring.skyhigh.repository.SeatConfigurationRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {PlaneRepository.class, PlaneModelRepository.class, SeatConfigurationRepository.class})
public abstract class PlaneMapper extends EntityMapper<String, Plane, PlaneDTO> {

    private PlaneRepository repository;
    private PlaneModelRepository planeModelRepository;
    private SeatConfigurationRepository seatConfigurationRepository;

    @Override
    protected JpaRepository<Plane, String> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Plane> createInstance() {
        return Plane::new;
    }

    @Autowired
    public void setRepository(PlaneRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPlaneModelRepository(PlaneModelRepository planeModelRepository) {
        this.planeModelRepository = planeModelRepository;
    }

    @Autowired
    public void setSeatConfigurationRepository(SeatConfigurationRepository seatConfigurationRepository) {
        this.seatConfigurationRepository = seatConfigurationRepository;
    }

    @Mappings({
            @Mapping(source = "model", target = "model", qualifiedByName = "planeModelToId"),
            @Mapping(source = "seatConfiguration", target = "seatConfiguration", qualifiedByName = "seatConfigurationToId")
    })
    @Override
    public abstract PlaneDTO toDto(Plane entity);

    @Mappings({
            @Mapping(source = "model", target = "model", qualifiedByName = "idToPlaneModel"),
            @Mapping(source = "seatConfiguration", target = "seatConfiguration", qualifiedByName = "idToSeatConfiguration")
    })
    @Override
    public abstract Plane map(PlaneDTO planeDTO, @MappingTarget Plane entity);

    @Named("planeModelToId")
    protected Long planeModelToId(PlaneModel model) {
        return model.getId();
    }

    @Named("idToPlaneModel")
    protected PlaneModel idToPlaneModel(Long id) {
        return planeModelRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Named("seatConfigurationToId")
    protected Long seatConfigurationToId(SeatConfiguration seatConfiguration) {
        return seatConfiguration.getId();
    }

    @Named("idToSeatConfiguration")
    protected SeatConfiguration idToSeatConfiguration(Long id) {
        return seatConfigurationRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
