package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.util.Identifiable;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

public abstract class EntityMapper<ID, E extends Identifiable<ID>, DTO extends Identifiable<ID>> {
    protected abstract JpaRepository<E, ID> getRepository();
    protected abstract Supplier<E> createInstance();
    public final E toEntity(DTO dto) {
        if(dto == null) return null;

        E entity = createInstance().get();
        if(dto.getId() != null) {
            entity = getRepository().findById(dto.getId()).orElse(createInstance().get());
        }

        return map(dto, entity);
    }
    public abstract DTO toDto(E entity);
    public abstract E map(DTO dto, @MappingTarget E entity);
}
