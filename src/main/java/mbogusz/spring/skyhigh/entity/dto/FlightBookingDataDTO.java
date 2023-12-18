package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightBookingDataDTO implements Identifiable<Long> {
    @Schema(name = "id", description = "Flight ID", example = "1")
    private Long id;
    @Schema(name = "planeModel", description = "Full name of the plane used in the flight", example = "Airbus A320-200 v1")
    private String planeModel;
    @JsonProperty("seatConfig")
    @Schema(name = "seatConfig", description = "Seat configuration of the plane")
    private SeatConfigurationBookingDataDTO seatConfig;
    @JsonProperty("seats")
    @Schema(name = "seats", description = "All seats in the plane and their status")
    private List<SeatBookingDataDTO> seats = new ArrayList<>();
}
