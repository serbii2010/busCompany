package net.thumbtack.school.buscompany.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Trip;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        date = LocalDate.parse(dateString);
    }

    public DateTrip getDateTrip(Trip trip) {
        init();
        return new DateTrip(1, trip, getDate(), null);
    }
}
