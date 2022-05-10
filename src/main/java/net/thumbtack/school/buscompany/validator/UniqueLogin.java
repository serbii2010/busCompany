package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = net.thumbtack.school.buscompany.validator.UniqueLoginValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLogin {
    String message() default "Login not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
