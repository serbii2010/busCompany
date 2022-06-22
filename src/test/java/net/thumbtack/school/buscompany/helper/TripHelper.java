package net.thumbtack.school.buscompany.helper;

import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class TripHelper {
    @Autowired
    private DateTripHelper dateTripHelper;

    private Trip trip;
    private Bus bus;

    public void init() {
        bus = new Bus(1, "Пазик", 21);
        Station fromStation = new Station(1, "omsk");
        Station toStation = new Station(2, "novosibirsk");
        //@todo заполнить поля расписания
        Schedule schedule = new Schedule();
        List<DateTrip> dates = new ArrayList<>();
        trip = new Trip(1, bus, fromStation, toStation, "12:30", "23:51", 30, false, schedule, dates);

        dates.add(dateTripHelper.getDateTrip(trip));
        trip.setDates(dates);
    }
}
