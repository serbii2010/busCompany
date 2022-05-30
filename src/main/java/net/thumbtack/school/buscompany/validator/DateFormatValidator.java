package net.thumbtack.school.buscompany.validator;

import org.apache.commons.validator.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator  implements ConstraintValidator<DateFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return GenericValidator.isDate(value, "yyyy-MM-dd", true);
    }
}
