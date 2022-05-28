package net.thumbtack.school.buscompany.validator;

import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class IsBusExistValidator implements ConstraintValidator<IsBusExist, String> {
    @Autowired
    private BusService busService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        try {
            busService.findByName(name);
            return true;
        } catch (ServerException ex) {
            return false;
        }
    }
}
