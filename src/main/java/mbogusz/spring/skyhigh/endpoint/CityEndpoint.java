package mbogusz.spring.skyhigh.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cities")
public class CityEndpoint {
    private final AirportRepository repository;

    @Autowired
    public CityEndpoint(AirportRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all cities that can be flown to", description = "Returns all cities with at least one airport")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class))))
    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        return new ResponseEntity<>(repository.findAllCities(), HttpStatus.OK);
    }
}
