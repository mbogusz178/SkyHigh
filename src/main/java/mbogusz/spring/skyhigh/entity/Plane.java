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
@Table(name = "PLANE")
public class Plane {

    @Id
    @Column(name = "REG_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODEL", nullable = false)
    private PlaneModel model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEAT_CONFIGURATION", nullable = false)
    private SeatConfiguration seatConfiguration;

}
