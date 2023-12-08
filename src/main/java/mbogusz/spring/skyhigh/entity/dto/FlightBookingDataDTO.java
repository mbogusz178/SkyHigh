package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long id;
    private String planeModel;
    @JsonProperty("seatConfig")
    private SeatConfigurationBookingDataDTO seatConfig;
    @JsonProperty("seats")
    private List<SeatBookingDataDTO> seats = new ArrayList<>();
}
