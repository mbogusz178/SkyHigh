package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long id;
    @JsonProperty("source")
    private AirportDTO source;
    @JsonProperty("destination")
    private AirportDTO destination;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Timestamp departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Timestamp arrivalDate;
    private Double totalTicketPrice;
}
