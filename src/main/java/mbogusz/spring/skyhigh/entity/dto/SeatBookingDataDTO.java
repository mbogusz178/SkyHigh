package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatBookingDataDTO {
    @Schema(name = "rowNumber", description = "Row number of the seat", example = "1")
    private int rowNumber;
    @Schema(name = "seatLetter", description = "Seat letter - the row is ordered alphabetically", example = "A")
    private String seatLetter;
    @Schema(name = "flightClass", description = "The flight class code according to IATA fare basis codes", example = "Y")
    private String flightClass;
    @Schema(name = "flightClassName", description = "Name of the flight class", example = "Economy class")
    private String flightClassName;
    @Schema(name = "status", description = "Seat status: AVAILABLE (seat is available), UNAVAILABLE (seat can't be booked in this plane or configuration) or BOOKED (seat is already booked)", example = "AVAILABLE")
    private String status;
}
