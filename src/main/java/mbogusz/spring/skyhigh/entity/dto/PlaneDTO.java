package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import mbogusz.spring.skyhigh.util.Identifiable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class PlaneDTO implements Identifiable<String> {
    private String id;
    private Long model;
    private Long seatConfiguration;
}
