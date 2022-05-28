package net.thumbtack.school.buscompany.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsSetScheduleOrDatesFieldValidator implements ConstraintValidator<IsSetScheduleOrDatesField, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Object schedule = new BeanWrapperImpl(obj).getPropertyValue("schedule");
        Object dates = new BeanWrapperImpl(obj).getPropertyValue("dates");

        boolean isSetOne = dates != null || schedule != null;
        boolean isSetTwo = dates != null && schedule != null;

        return isSetOne && !isSetTwo;
    }
}
