package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;

@Getter
@Setter
@Component
public class TicketHelper {
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private DateTripHelper dateTripHelper;

//    private static TicketHelper instance = null;

    private Passenger passenger;
    private Place place;

//    private TicketHelper() throws ParseException {
////        init();
//    }

//    public static TicketHelper getInstance() throws ParseException {
//        if (instance == null) {
//            instance = new TicketHelper();
//        }
//        return instance;
//    }

    @PostMapping
    public void init() throws ParseException {
        passenger = new Passenger(1, "имя", "фамилия", "passport");
        place = new Place(1, 2, passenger, dateTripHelper.getDateTrip(tripHelper.getTrip()));
    }
}
