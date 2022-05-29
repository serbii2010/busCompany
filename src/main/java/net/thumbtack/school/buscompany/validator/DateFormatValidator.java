package net.thumbtack.school.buscompany.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator  implements ConstraintValidator<DateFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            format.parse(value);
        } catch (ParseException ex) {
            return false;
        }

        return true;
    }
}
