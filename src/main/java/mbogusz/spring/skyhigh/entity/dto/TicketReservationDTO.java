package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbogusz.spring.skyhigh.entity.validation.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketReservationDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TicketMainReservationDTO {
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
        @NotNull
        @NotEmpty(message = "Wpisz miasto")
        private String city;
        @NotNull
        @NotEmpty(message = "Wpisz kraj")
        private String country;
        @NotNull
        @NotEmpty(message = "Wpisz numer telefonu")
        @PhoneNumber
        private String phoneNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TicketOtherReservationsDTO {
        @NotNull
        private Integer rowNumber;
        @NotNull
        private String seatLetter;
        @NotNull
        @NotEmpty
        private String ageGroup;
        @NotNull
        @NotEmpty(message = "Wpisz imię")
        private String firstName;
        @NotNull
        @NotEmpty(message = "Wpisz nazwisko")
        private String lastName;
    }

    private Long passenger;
    private Long flightId;
    @Valid
    @JsonProperty("mainReservation")
    private TicketMainReservationDTO mainReservation;
    @Valid
    @JsonProperty("otherPassengers")
    private List<TicketOtherReservationsDTO> otherPassengers = new ArrayList<>();
}
