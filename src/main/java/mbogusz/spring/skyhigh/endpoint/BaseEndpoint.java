package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.util.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.List;


public abstract class BaseEndpoint<ID, E extends Identifiable<ID>> {

    public abstract JpaRepository<E, ID> getRepository();
    @GetMapping("/")
    public ResponseEntity<List<E>> getAll() {
        return new ResponseEntity<>(getRepository().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<E> getById(@PathVariable ID id) {
        return new ResponseEntity<>(getRepository().findById(id).orElseThrow(NotFoundException::new), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<E> create(@RequestBody E e) {
        return new ResponseEntity<>(getRepository().save(e), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> update(@RequestBody E e, @PathVariable ID id) {
        e.setId(id);
        return new ResponseEntity<>(getRepository().save(e), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable ID id) {
        getRepository().deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
