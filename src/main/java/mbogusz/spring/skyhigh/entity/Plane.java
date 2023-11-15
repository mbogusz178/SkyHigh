package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plane")
public class Plane {

    @Id
    @Column(name = "reg_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model", nullable = false)
    private PlaneModel model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_configuration", nullable = false)
    private SeatConfiguration seatConfiguration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_class", nullable = false)
    private FlightClass flightClass;

}