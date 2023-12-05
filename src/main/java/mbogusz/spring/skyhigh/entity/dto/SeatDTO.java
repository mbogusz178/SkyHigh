package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.entity.validation.ValidDTORowNumber;
import mbogusz.spring.skyhigh.entity.validation.ValidRowNumber;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatDTO implements Identifiable<Long> {
    private Long id;
    private String plane;
    @ValidDTORowNumber
    private int rowNumber;
    @Pattern(regexp = "^[A-Z]$")
    private char seatLetter;
    private Long flightClass;
    private String seatStatus;
}