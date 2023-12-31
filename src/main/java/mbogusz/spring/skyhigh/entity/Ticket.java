package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
    @SequenceGenerator(name = "ticket_generator", sequenceName = "ticket_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger", nullable = false)
    private Passenger passenger;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false)
    private AgeGroup ageGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_class", nullable = false)
    private FlightClass flightClass;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "seat", nullable = false)
    private Seat seat;

    @Column(name = "date_booked", nullable = false)
    private Timestamp dateBooked;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @Column(name = "notified", nullable = false)
    private Boolean notified;
}
