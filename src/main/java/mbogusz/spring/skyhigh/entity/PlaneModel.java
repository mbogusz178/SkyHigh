package mbogusz.spring.skyhigh.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PLANE_MODEL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"manufacturer", "family", "model_number", "version"})
})
public class PlaneModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plane_model_generator")
    @SequenceGenerator(name = "plane_model_generator", sequenceName = "plane_model_sequence")
    private Long id;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "FAMILY")
    private String family;

    @Column(name = "MODEL_NUMBER")
    private int modelNumber;

    @Column(name = "VERSION")
    private int version;

    @Formula("concat(manufacturer, ' ', family, '-', model_number, ' v', version)")
    private String fullName;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Plane> planesOfModel = new HashSet<>();
}
