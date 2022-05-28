package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsSetScheduleOrDatesFieldValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsSetScheduleOrDatesField {
    String message() default "Set field schedule or dates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}