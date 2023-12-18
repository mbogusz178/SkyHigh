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
public class SeatConfigurationBookingDataDTO {
    @Schema(name = "rowConfig", description = "Row configuration string, consisting of seat groups separated by aisles (marked as dashes)", example = "3-3")
    private String rowConfig;
    @Schema(name = "numRows", description = "Number of seat rows in the plane", example = "2")
    private int numRows;
    @Schema(name = "numColumns", description = "Total number of seats in one row, derived from sum of numbers from rowConfig", example = "6")
    private int numColumns;
}
