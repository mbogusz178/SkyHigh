package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plane")
public class Plane implements Identifiable<String> {

    @Id
    @Column(name = "reg_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model", nullable = false)
    private PlaneModel model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_configuration", nullable = false)
    private SeatConfiguration seatConfiguration;
}
