package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOrderInDatesTripValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOrderInDatesTrip {
    String message() default "Date not find in trip";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}