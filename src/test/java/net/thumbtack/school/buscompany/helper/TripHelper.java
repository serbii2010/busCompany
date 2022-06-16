package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import net.thumbtack.school.buscompany.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
public class TripHelper {
    private static TripHelper instance = null;

    private TripHelper() throws ParseException {
        init();
    }

    private Trip trip;

    public static TripHelper getInstance() throws ParseException {
        if (instance == null) {
            instance = new TripHelper();
        }
        return instance;
    }

    public void init() throws ParseException {
        Bus bus = new Bus(1, "Пазик", 21);
        Station fromStation = new Station(1, "omsk");
        Station toStation = new Station(2, "novosibirsk");
        Schedule schedule = new Schedule();
        List<DateTrip> dates = new ArrayList<>();
        trip = new Trip(1, bus, fromStation, toStation, "12:30", "23:51", 30, false, schedule, dates);

        dates.add(DateTripHelper.getInstance().getDateTrip());
        trip.setDates(dates);
    }
}
