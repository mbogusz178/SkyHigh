package mbogusz.spring.skyhigh.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(name = "rowNumber", description = "Row number of seat to be booked", example = "1")
        private Integer rowNumber;
        @NotNull
        @Schema(name = "seatLetter", description = "Seat letter of seat to be booked", example = "A")
        private String seatLetter;
        @NotNull
        @NotEmpty(message = "Wpisz imię")
        @Schema(name = "firstName", description = "First name of the person booking the reservation", example = "John")
        private String firstName;
        @NotNull
        @NotEmpty(message = "Wpisz nazwisko")
        @Schema(name = "lastName", description = "Last name of the person booking the reservation", example = "Doe")
        private String lastName;
        @NotNull
        @NotEmpty(message = "Wpisz miasto")
        @Schema(name = "city", description = "City of the person booking the reservation", example = "New York")
        private String city;
        @NotNull
        @NotEmpty(message = "Wpisz kraj")
        @Schema(name = "country", description = "Country of the person booking the reservation", example = "USA")
        private String country;
        @NotNull
        @NotEmpty(message = "Wpisz numer telefonu")
        @PhoneNumber
        @Schema(name = "phoneNumber", description = "Phone number of the person booking the reservation, compliant with Polish standards", example = "600 600 600")
        private String phoneNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TicketOtherReservationsDTO {
        @NotNull
        @Schema(name = "rowNumber", description = "Row number of seat to be booked", example = "1")
        private Integer rowNumber;
        @NotNull
        @Schema(name = "seatLetter", description = "Seat letter of seat to be booked", example = "A")
        private String seatLetter;
        @NotNull
        @NotEmpty
        @Schema(name = "ageGroup", description = "Age group of the passenger, playing a role in the ticket price; possible values are ADULT and CHILD", example = "ADULT")
        private String ageGroup;
        @NotNull
        @NotEmpty(message = "Wpisz imię")
        @Schema(name = "firstName", description = "First name of the person taking the seat", example = "Jane")
        private String firstName;
        @NotNull
        @NotEmpty(message = "Wpisz nazwisko")
        @Schema(name = "lastName", description = "Last name of the person taking the seat", example = "Doe")
        private String lastName;
    }

    @Schema(name = "passenger", description = "User ID of person booking the reservation", example = "1")
    private Long passenger;
    @Schema(name = "flightId", description = "Flight ID being booked", example = "1")
    private Long flightId;
    @Valid
    @JsonProperty("mainReservation")
    @Schema(name = "mainReservation")
    private TicketMainReservationDTO mainReservation;
    @Valid
    @JsonProperty("otherPassengers")
    @Schema(name = "otherPassengers")
    private List<TicketOtherReservationsDTO> otherPassengers = new ArrayList<>();
}
