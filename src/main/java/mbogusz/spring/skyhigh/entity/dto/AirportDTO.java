package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportDTO implements Identifiable<String> {
    @Schema(name = "id", description = "Airport IATA code", example = "JFK")
    private String id;
    @Schema(name = "airportName", description = "Airport name", example = "John F. Kennedy International Airport")
    private String airportName;
    @Schema(name = "city", description = "City being the location of airport", example = "New York")
    private String city;
    @Schema(name = "country", description = "Country where the airport is located", example = "USA")
    private String country;
}
