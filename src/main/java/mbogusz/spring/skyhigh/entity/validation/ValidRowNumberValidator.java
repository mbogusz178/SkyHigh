package mbogusz.spring.skyhigh.entity.validation;

import mbogusz.spring.skyhigh.entity.Seat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRowNumberValidator implements ConstraintValidator<ValidRowNumber, Seat> {

    @Override
    public boolean isValid(Seat value, ConstraintValidatorContext context) {
        int rowNumber = value.getRowNumber();

        int maxRows = value.getFlight().getPlane().getSeatConfiguration().getNumRows();

        return rowNumber <= maxRows;
    }
}
