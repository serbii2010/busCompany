package net.thumbtack.school.buscompany.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MinPasswordLengthValidator implements ConstraintValidator<MinPasswordLength, String> {
    @Value("${min_password_length}")
    private int minPasswordLength;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.length() > minPasswordLength;
    }
}
