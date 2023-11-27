package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO implements Identifiable<Long> {
    private Long id;
    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
}
