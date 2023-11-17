package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat_configuration")
public class SeatConfiguration implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_config_generator")
    @SequenceGenerator(name = "seat_config_generator", sequenceName = "seat_config_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @Column(name = "num_rows", nullable = false)
    private int numRows;

    @Column(name = "row_config", nullable = false)
    private String numConfig;

    @OneToMany(mappedBy = "seatConfiguration", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Plane> planesWithSeatConfig = new HashSet<>();

    public int getSeatCount() {
        String[] aisleConfigs = numConfig.split("-");
        int rowSize = Arrays.stream(aisleConfigs).mapToInt(Integer::parseInt).sum();
        return rowSize * numRows;
    }
}
