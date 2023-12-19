package mbogusz.spring.skyhigh.entity.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber polishNumber = phoneNumberUtil.parse(value, "PL");
            return phoneNumberUtil.isValidNumber(polishNumber);
        } catch (NumberParseException e) {
            try {
                Phonenumber.PhoneNumber americanNumber = phoneNumberUtil.parse(value, "US");
                return phoneNumberUtil.isValidNumber(americanNumber);
            } catch (NumberParseException ex) {
                return false;
            }
        }
    }
}
