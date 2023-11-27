package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight_class")
public class FlightClass implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_class_generator")
    @SequenceGenerator(name = "flight_class_generator", sequenceName = "flight_class_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @Column(name = "class_name", nullable = false, unique = true)
    private String className;

    @Column(name = "max_hand_luggage_wgt", nullable = false)
    private int maxHandLuggageWeight;

    @Column(name = "max_checked_baggage_wgt")
    private int maxCheckedBaggageWeight;

    @Column(name = "max_hand_luggage_dimensions")
    private String maxHandLuggageDimensions;
}
