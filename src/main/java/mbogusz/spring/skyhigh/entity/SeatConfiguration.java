package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "SEAT_CONFIGURATION")
public class SeatConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_config_generator")
    @SequenceGenerator(name = "seat_config_generator", sequenceName = "seat_config_sequence")
    private Long id;

    @Column(name = "NUM_ROWS", nullable = false)
    private int numRows;

    @Column(name = "ROW_CONFIG", nullable = false)
    private String numConfig;

    @OneToMany(mappedBy = "seatConfiguration", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Plane> planesWithSeatConfig = new HashSet<>();

    public int getSeatCount() {
        String[] aisleConfigs = numConfig.split("-");
        int rowSize = Arrays.stream(aisleConfigs).map(Integer::parseInt).mapToInt(i -> i).sum();
        return rowSize * numRows;
    }
}
