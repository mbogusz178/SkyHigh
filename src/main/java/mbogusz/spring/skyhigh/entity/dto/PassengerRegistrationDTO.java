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
    @Email(message = "Nieprawidłowy format adresu email")
    @NotEmpty(message = "Wpisz adres email")
    private String email;
    @NotNull
    @NotEmpty(message = "Wpisz hasło")
    private String password;
    @NotNull
    @NotEmpty(message = "Wpisz ponownie hasło")
    private String matchingPassword;
    @NotNull
    @NotEmpty(message = "Wpisz imię")
    private String firstName;
    @NotNull
    @NotEmpty(message = "Wpisz nazwisko")
    private String lastName;
    @NotNull
    @NotEmpty(message = "Wpisz miasto")
    private String city;
    @NotNull
    @NotEmpty(message = "Wpisz kraj")
    private String country;
}
