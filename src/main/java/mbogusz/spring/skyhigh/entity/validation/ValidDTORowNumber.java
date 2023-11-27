package mbogusz.spring.skyhigh.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDTORowNumberValidator.class)
public @interface ValidDTORowNumber {

    String message() default "Seat won't fit in the plane";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
