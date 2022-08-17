package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class TripHelper {
    @Autowired
    private DateTripHelper dateTripHelper;

    private Trip trip;
    private Bus bus;
    private Schedule schedule;
    private Station fromStation;
    private Station toStation;

    public void init() {
        bus = new Bus(1, "Пазик", 21);
        fromStation = new Station(1, "omsk");
        toStation = new Station(2, "novosibirsk");

        dateTripHelper.init("2021-12-12");
        Date fromDate = dateTripHelper.getDate();
        dateTripHelper.init("2022-12-12");
        Date toDate = dateTripHelper.getDate();
        schedule = new Schedule(1, fromDate, toDate, "odd", null);
        List<DateTrip> dates = new ArrayList<>();
        trip = new Trip(1, bus, fromStation, toStation, "12:30", "23:51", 30, false, schedule, dates);

        dates.add(dateTripHelper.getDateTrip(trip));
        trip.setDates(dates);
    }
}
