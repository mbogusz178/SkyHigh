package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityEndpoint {
    private final AirportRepository repository;

    @Autowired
    public CityEndpoint(AirportRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        return new ResponseEntity<>(repository.findAllCities(), HttpStatus.OK);
    }
}
