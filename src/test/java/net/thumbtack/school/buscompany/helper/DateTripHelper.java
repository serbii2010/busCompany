package net.thumbtack.school.buscompany.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DateTripHelper {
    private LocalDate date;

    public void init() {
        init("2021-12-12");
    }

    public void init(String dateString) {
        //@todo
//        try {
//            date = (new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)).parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    public DateTrip getDateTrip(Trip trip) {
        init();
        return new DateTrip(1, trip, getDate(), null);
    }
}
