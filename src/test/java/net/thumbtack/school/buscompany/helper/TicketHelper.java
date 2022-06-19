package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;

import java.text.ParseException;

@Getter
public class TicketHelper {
    private static TicketHelper instance = null;

    private Passenger passenger;
    private Place place;

    private TicketHelper() throws ParseException {
        init();
    }

    public static TicketHelper getInstance() throws ParseException {
        if (instance == null) {
            instance = new TicketHelper();
        }
        return instance;
    }

    public void init() throws ParseException {
        passenger = new Passenger(1, "имя", "фамилия", "passport");
        place = new Place(1, 2, passenger, DateTripHelper.getInstance().getDateTrip(TripHelper.getInstance().getTrip()));
    }
}
