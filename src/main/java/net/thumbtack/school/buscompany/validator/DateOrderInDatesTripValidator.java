package net.thumbtack.school.buscompany.validator;

import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class DateOrderInDatesTripValidator implements ConstraintValidator<DateOrderInDatesTrip, Object> {
    @Autowired
    private TripService tripService;

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        String date = (String) new BeanWrapperImpl(obj).getPropertyValue("date");
        Integer tripId = (Integer) new BeanWrapperImpl(obj).getPropertyValue("tripId");

        try {
            Trip trip = tripService.findById(String.valueOf(tripId));
            for (DateTrip dateTrip: trip.getDates()) {
                if (date.equals( dateTrip.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) )  {
                    return true;
                }
            }
            return false;
        } catch (ServerException exception) {
           return false;
        }

    }
}
