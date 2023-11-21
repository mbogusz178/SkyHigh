package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightClassDTO implements Identifiable<Long> {
    private Long id;
    private String className;
    private int maxHandLuggageWeight;
    private int maxCheckedBaggageWeight;
    private String maxHandLuggageDimensions;
}
