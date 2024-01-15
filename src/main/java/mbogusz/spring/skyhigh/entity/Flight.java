package mbogusz.spring.skyhigh.entity;

import lombok.*;
import mbogusz.spring.skyhigh.testUtils.annotations.TestEntityValue;
import mbogusz.spring.skyhigh.testUtils.annotations.TestEntityValues;
import mbogusz.spring.skyhigh.testUtils.mappers.AirportTestProvider;
import mbogusz.spring.skyhigh.testUtils.mappers.ParseDoubleTestProvider;
import mbogusz.spring.skyhigh.testUtils.mappers.ParseLongTestProvider;
import mbogusz.spring.skyhigh.testUtils.mappers.TimestampTestProvider;
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
@TestEntityValues({
        @TestEntityValue(fieldName = "id", fieldValue = "0", valueMapper = ParseLongTestProvider.class),
        @TestEntityValue(fieldName = "source", fieldValue = "sample_airport_source", valueMapper = AirportTestProvider.class),
        @TestEntityValue(fieldName = "destination", fieldValue = "sample_airport_dest", valueMapper = AirportTestProvider.class),
        @TestEntityValue(fieldName = "plane", autoLookup = true),
        @TestEntityValue(fieldName = "departureDate", fieldValue = "2024-01-12T12:00:00Z", valueMapper = TimestampTestProvider.class),
        @TestEntityValue(fieldName = "arrivalDate", fieldValue = "2024-01-12T13:10:00Z", valueMapper = TimestampTestProvider.class),
        @TestEntityValue(fieldName = "ticketPriceAdult", fieldValue = "30.0", valueMapper = ParseDoubleTestProvider.class),
        @TestEntityValue(fieldName = "ticketPriceChild", fieldValue = "15.0", valueMapper = ParseDoubleTestProvider.class)
})
@ToString
public class Flight implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator", sequenceName = "flight_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "source", nullable = false)
    private Airport source;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "destination", nullable = false)
    private Airport destination;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "plane", nullable = false)
    private Plane plane;

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
