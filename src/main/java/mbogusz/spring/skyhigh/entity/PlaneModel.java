package mbogusz.spring.skyhigh.entity;

import lombok.*;
import mbogusz.spring.skyhigh.util.Identifiable;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plane_model", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"manufacturer", "family", "model_number", "version"})
})
@ToString
public class PlaneModel implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plane_model_generator")
    @SequenceGenerator(name = "plane_model_generator", sequenceName = "plane_model_sequence", initialValue = 0, allocationSize = 1)
    private Long id;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "family", nullable = false)
    private String family;

    @Column(name = "model_number", nullable = false)
    private int modelNumber;

    @Column(name = "version", nullable = false)
    private int version;

    @Formula("concat(manufacturer, ' ', family, '-', model_number, ' v', version)")
    private String fullName;

//    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Set<Plane> planesOfModel = new HashSet<>();
}
