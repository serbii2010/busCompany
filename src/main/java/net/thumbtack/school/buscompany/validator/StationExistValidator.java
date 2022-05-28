package net.thumbtack.school.buscompany.validator;

import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class StationExistValidator implements ConstraintValidator<IsStationExist, String> {
    @Autowired
    private StationService stationService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        try {
            stationService.findStationByName(name);
            return true;
        } catch (ServerException ex) {
            return false;
        }
    }
}
