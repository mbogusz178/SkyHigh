package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import mbogusz.spring.skyhigh.entity.validation.PasswordMatches;
import mbogusz.spring.skyhigh.util.Identifiable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@PasswordMatches
public class PassengerRegistrationDTO {
    @Email
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String country;
}
