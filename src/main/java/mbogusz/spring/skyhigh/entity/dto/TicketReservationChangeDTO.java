package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.entity.validation.PhoneNumber;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketReservationChangeDTO {
    @NotNull
    private Long ticketId;
    @NotNull
    private Integer rowNumber;
    @NotNull
    private String seatLetter;
    @NotNull
    @NotEmpty(message = "Wpisz imię")
    private String firstName;
    @NotNull
    @NotEmpty(message = "Wpisz nazwisko")
    private String lastName;
}
