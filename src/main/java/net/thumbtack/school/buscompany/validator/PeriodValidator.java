package net.thumbtack.school.buscompany.validator;


import net.thumbtack.school.buscompany.service.trip.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PeriodValidator implements ConstraintValidator<Period, String>  {
    @Autowired
    private ScheduleService scheduleService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return scheduleService.checkPeriod(value);
    }
}
