package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StationExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StationExist {
    String message() default "Field length is less than allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
