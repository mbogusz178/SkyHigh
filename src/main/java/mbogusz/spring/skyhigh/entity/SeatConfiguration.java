package mbogusz.spring.skyhigh.entity;

import lombok.*;
import mbogusz.spring.skyhigh.util.Identifiable;

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
@ToString
public class SeatConfiguration implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_config_generator")
    @SequenceGenerator(name = "seat_config_generator", sequenceName = "seat_config_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @Column(name = "num_rows", nullable = false)
    private int numRows;

    @Column(name = "row_config", nullable = false)
    private String rowConfig;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = SeatClassRange.class)
    @JoinTable(name = "seat_class_ranges_seat_config", joinColumns = { @JoinColumn(name = "seat_config_id") }, inverseJoinColumns = { @JoinColumn(name = "seat_class_range_id") })
    private Set<SeatClassRange> seatClassRanges = new HashSet<>();

//    @OneToMany(mappedBy = "seatConfiguration", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Set<Plane> planesWithSeatConfig = new HashSet<>();

    public int getSeatCount() {
        return getRowSize() * numRows;
    }

    public int getRowSize() {
        String[] aisleConfigs = rowConfig.split("-");
        return Arrays.stream(aisleConfigs).mapToInt(Integer::parseInt).sum();
    }
}
