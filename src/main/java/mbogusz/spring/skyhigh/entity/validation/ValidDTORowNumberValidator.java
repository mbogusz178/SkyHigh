package mbogusz.spring.skyhigh.entity.validation;

import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ValidDTORowNumberValidator implements ConstraintValidator<ValidRowNumber, Long> {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        Optional<Seat> possibleSeat = seatRepository.findById(value);

        if(possibleSeat.isEmpty()) return false;

        Seat seat = possibleSeat.get();

        int rowNumber = seat.getRowNumber();

        int maxRows = seat.getFlight().getPlane().getSeatConfiguration().getNumRows();

        return rowNumber < maxRows;
    }
}
