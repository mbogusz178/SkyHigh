package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight")
public class Flight implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator", sequenceName = "flight_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source", nullable = false)
    private Airport source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination", nullable = false)
    private Airport destination;

    @Column(name = "departure_date", nullable = false)
    private Timestamp departureDate;

    @Column(name = "arrival_date", nullable = false)
    private Timestamp arrivalDate;

    @Column(name = "ticket_price_adult", nullable = false)
    private Double ticketPriceAdult;

    @Column(name = "ticket_price_child", nullable = false)
    private Double ticketPriceChild;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Seat> flightSeats;
}
