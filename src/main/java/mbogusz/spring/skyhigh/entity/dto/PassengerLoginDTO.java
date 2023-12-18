package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerLoginDTO {
    @Email(message = "Nieprawidłowy format adresu email")
    @NotEmpty(message = "Wpisz adres email")
    @Schema(name = "email", description = "Email address", example = "example@example.com")
    private String email;
    @NotEmpty(message = "Wpisz hasło")
    @Schema(name = "passsword", description = "Password for the account")
    private String password;
}
