package mbogusz.spring.skyhigh.entity.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passenger")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
@ToString
public class Passenger extends User {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("PASSENGER"));
    }
}
