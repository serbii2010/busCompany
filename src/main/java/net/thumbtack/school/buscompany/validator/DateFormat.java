package net.thumbtack.school.buscompany.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    String message() default "Bad date format. Set date in format 'yyyy-MM-dd'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
