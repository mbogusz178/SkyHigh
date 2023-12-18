package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "ticketId", description = "Ticket ID", example = "1")
    private Long ticketId;
    @NotNull
    @Schema(name = "rowNumber", description = "New row number", example = "1")
    private Integer rowNumber;
    @NotNull
    @Schema(name = "seatLetter", description = "New seat letter", example = "A")
    private String seatLetter;
    @NotNull
    @NotEmpty(message = "Wpisz imię")
    @Schema(name = "firstName", description = "New first name", example = "Tom")
    private String firstName;
    @NotNull
    @NotEmpty(message = "Wpisz nazwisko")
    @Schema(name = "lastName", description = "New last name", example = "Brown")
    private String lastName;
}
