package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PassengerGetDTO implements Identifiable<Long> {
    @Schema(name = "id", description = "User ID", example = "1")
    private Long id;
    @Email
    @Schema(name = "email", description = "User email", example = "example@example.com")
    private String email;
    @Schema(name = "firstName", description = "First name of user", example = "John")
    private String firstName;
    @Schema(name = "lastName", description = "Last name of user", example = "Doe")
    private String lastName;
    @Schema(name = "city", description = "City of user", example = "New York")
    private String city;
    @Schema(name = "country", description = "Country of user", example = "USA")
    private String country;
}
