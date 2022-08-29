package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Getter
@Setter
@Component
public class TicketHelper {
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private DateTripHelper dateTripHelper;

    private Passenger passenger;
    private Place place;
    private Place freePlace;

    public void init() throws ParseException {
        passenger = new Passenger(1, "имя", "фамилия", "passport");
        place = new Place(1, 2, passenger, dateTripHelper.getDateTrip(tripHelper.getTrip()));
        freePlace = new Place(1, 2, null, dateTripHelper.getDateTrip(tripHelper.getTrip()));
    }
}
