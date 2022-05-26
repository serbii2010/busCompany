package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BusExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BusExist {
    String message() default "Field length is less than allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
