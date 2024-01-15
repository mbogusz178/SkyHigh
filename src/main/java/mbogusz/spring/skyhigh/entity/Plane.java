package mbogusz.spring.skyhigh.entity;

import lombok.*;
import mbogusz.spring.skyhigh.testUtils.annotations.TestEntityValue;
import mbogusz.spring.skyhigh.testUtils.annotations.TestEntityValues;
import mbogusz.spring.skyhigh.testUtils.mappers.PlaneModelTestProvider;
import mbogusz.spring.skyhigh.testUtils.mappers.SeatConfigurationTestProvider;
import mbogusz.spring.skyhigh.testUtils.mappers.StringAsIs;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plane")
@TestEntityValues({
        @TestEntityValue(fieldName = "id", fieldValue = "SKY-AA", autoLookup = true),
        @TestEntityValue(fieldName = "model", fieldValue = "sample_model", valueMapper = PlaneModelTestProvider.class, autoLookup = true),
        @TestEntityValue(fieldName = "seatConfiguration", fieldValue = "sample_seat_configuration", valueMapper = SeatConfigurationTestProvider.class, autoLookup = true)
})
@ToString
public class Plane implements Identifiable<String> {

    @Id
    @Column(name = "reg_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "model", nullable = false)
    private PlaneModel model;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "seat_configuration", nullable = false)
    private SeatConfiguration seatConfiguration;
}
