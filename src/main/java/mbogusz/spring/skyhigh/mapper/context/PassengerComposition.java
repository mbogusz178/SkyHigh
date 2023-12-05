package mbogusz.spring.skyhigh.mapper.context;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerComposition {
    private Integer adultCount;
    private Integer childCount;
}
