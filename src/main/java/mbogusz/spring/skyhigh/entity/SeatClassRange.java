package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat_class_ranges")
public class SeatClassRange implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_class_ranges_generator")
    @SequenceGenerator(name = "seat_class_ranges_generator", sequenceName = "seat_class_ranges_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = SeatConfiguration.class)
    @JoinTable(name = "seat_class_ranges_seat_config", joinColumns = { @JoinColumn(name = "seat_class_range_id") }, inverseJoinColumns = { @JoinColumn(name = "seat_config_id") })
    private Set<SeatConfiguration> seatConfigurations;

    @Column(name = "from_row", nullable = false)
    private Integer fromRow;

    @Column(name = "to_row", nullable = false)
    private Integer toRow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_class")
    private SeatClass seatClass;
}
