package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import mbogusz.spring.skyhigh.util.Identifiable;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
public class FlightDTO implements Identifiable<Long> {
    private Long id;
    private String source;
    private String destination;
    private String plane;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
//    @ToString.Exclude
    private Timestamp departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
//    @ToString.Exclude
    private Timestamp arrivalDate;
    private Double ticketPriceAdult;
    private Double ticketPriceChild;

//    @ToString.Include(name = "departureDate")
//    String departureDate() {
//        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(departureDate.getTime());
//    }
//
//    @ToString.Include(name = "arrivalDate")
//    String arrivalDate() {
//        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(arrivalDate.getTime());
//    }
}
