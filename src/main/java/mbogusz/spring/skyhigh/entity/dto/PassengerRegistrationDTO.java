package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "email", description = "Email address for account", example = "example@example.com")
    private String email;
    @NotNull
    @NotEmpty(message = "Wpisz hasło")
    @Schema(name = "password", description = "Password for the account")
    private String password;
    @NotNull
    @NotEmpty(message = "Wpisz ponownie hasło")
    @Schema(name = "matchingPassword", description = "Repeated password for the account; must be the same as the value of \"password\"")
    private String matchingPassword;
    @NotNull
    @NotEmpty(message = "Wpisz imię")
    @Schema(name = "firstName", description = "First name", example = "John")
    private String firstName;
    @NotNull
    @NotEmpty(message = "Wpisz nazwisko")
    @Schema(name = "lastName", description = "Last name", example = "Doe")
    private String lastName;
    @NotNull
    @NotEmpty(message = "Wpisz miasto")
    @Schema(name = "city", description = "City of residence", example = "New York")
    private String city;
    @NotNull
    @NotEmpty(message = "Wpisz kraj")
    @Schema(name = "country", description = "Country", example = "USA")
    private String country;
}
