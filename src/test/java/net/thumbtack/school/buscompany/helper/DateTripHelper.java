package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
public class DateTripHelper {
    private static DateTripHelper instance = null;

    private final Date date = (new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)).parse("2021-12-12");

    public static DateTripHelper getInstance() throws ParseException {
        if (instance == null) {
            instance = new DateTripHelper();
        }
        return instance;
    }

    private DateTripHelper() throws ParseException {
    }

    public DateTrip getDateTrip(Trip trip) {
        return new DateTrip(1, trip, getDate(), null);
    }
}