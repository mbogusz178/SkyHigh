package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.entity.validation.ValidRowNumber;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class Seat implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
    @SequenceGenerator(name = "seat_generator", sequenceName = "seat_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane", nullable = false)
    private Plane plane;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight", nullable = false)
    private Flight flight;

    @ValidRowNumber
    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Column(name = "seat_letter", nullable = false)
    @Pattern(regexp = "^[A-Z]$")
    private char seatLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_class", nullable = false)
    private FlightClass flightClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SeatStatus status;
}
