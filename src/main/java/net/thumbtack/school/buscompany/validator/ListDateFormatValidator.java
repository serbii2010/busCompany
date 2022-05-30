package net.thumbtack.school.buscompany.validator;

import org.apache.commons.validator.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ListDateFormatValidator implements ConstraintValidator<ListDateFormat, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (String date : value) {
            if (!GenericValidator.isDate(date, "yyyy-MM-dd", true)) {
                return false;
            }
        }
        return true;
    }
}
