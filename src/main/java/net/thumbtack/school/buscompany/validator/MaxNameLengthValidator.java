package net.thumbtack.school.buscompany.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MaxNameLengthValidator implements ConstraintValidator<MaxNameLength, String> {
    @Value("${max_name_length}")
    private int maxNameLength;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.length() <= maxNameLength;
    }
}
