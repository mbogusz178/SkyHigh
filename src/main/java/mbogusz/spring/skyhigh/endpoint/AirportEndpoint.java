package mbogusz.spring.skyhigh.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.dto.AirportDTO;
import mbogusz.spring.skyhigh.mapper.AirportMapper;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/airports")
@Tag(name = "Airports")
public class AirportEndpoint extends BaseEndpoint<String, Airport, AirportDTO> {

    private final AirportRepository repository;
    private final AirportMapper mapper;

    @Override
    public EntityMapper<String, Airport, AirportDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Airport, String> getRepository() {
        return repository;
    }

    @Autowired
    public AirportEndpoint(AirportRepository repository, AirportMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Operation(summary = "Get airports in city", description = "Returns all airports located in specified city")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AirportDTO.class))))
    @GetMapping("/city")
    public ResponseEntity<List<AirportDTO>> getByCity(@RequestParam @Parameter(name = "city", description = "The city where the airports are located", example = "New York") String city) {
        return new ResponseEntity<>(repository.findByCity(city).stream().map(mapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }
}
