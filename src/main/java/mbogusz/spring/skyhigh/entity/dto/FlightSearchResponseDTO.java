package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSearchResponseDTO implements Identifiable<Long> {
    @Schema(name = "id", description = "Flight ID", example = "1")
    private Long id;
    @JsonProperty("source")
    @Schema(name = "source", description = "Departing airport")
    private AirportDTO source;
    @JsonProperty("destination")
    @Schema(name = "destination", description = "Arrival airport")
    private AirportDTO destination;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Schema(name = "departureDate", description = "Departure date, formatted as yyyy-MM-ddTHH:mm:ssZ in UTC timezone", example = "2023-12-01T08:10:00Z")
    private Timestamp departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Schema(name = "arrivalDate", description = "Arrival date, formatted as yyyy-MM-ddTHH:mm:ssZ in UTC timezone", example = "2023-12-01T08:10:00Z")
    private Timestamp arrivalDate;
    @Schema(name = "totalTicketPrice", description = "Total ticket price", example = "500.00")
    private Double totalTicketPrice;
}
