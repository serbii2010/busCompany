package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = net.thumbtack.school.buscompany.validator.PeriodValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Period {
    String message() default "Bad format in period field";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
