package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.util.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;


public abstract class BaseEndpoint<ID, E extends Identifiable<ID>, DTO extends Identifiable<ID>> {

    public abstract EntityMapper<ID, E, DTO> getMapper();
    public abstract JpaRepository<E, ID> getRepository();
    @GetMapping("/")
    public ResponseEntity<List<DTO>> getAll() {
        return new ResponseEntity<>(getRepository().findAll().stream().map(e -> getMapper().toDto(e)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id) {
        return new ResponseEntity<>(getMapper().toDto(getRepository().findById(id).orElseThrow(NotFoundException::new)), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<DTO> create(@RequestBody DTO dto) {
        E entity = getMapper().toEntity(dto);
        E savedEntity = getRepository().save(entity);
        DTO savedDTO = getMapper().toDto(savedEntity);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@RequestBody DTO dto, @PathVariable ID id) {
        E entity = getMapper().toEntity(dto);
        entity.setId(id);
        E savedEntity = getRepository().save(entity);
        DTO savedDTO = getMapper().toDto(savedEntity);
        return new ResponseEntity<>(savedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable ID id) {
        getRepository().deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
