package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinPasswordLengthValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinPasswordLength {
    String message() default "Field length is too long.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
