package mbogusz.spring.skyhigh.entity.validation;

import mbogusz.spring.skyhigh.entity.dto.PassengerRegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PassengerRegistrationDTO> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PassengerRegistrationDTO value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getMatchingPassword());
    }
}
