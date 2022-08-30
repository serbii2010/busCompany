package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateTrip {
    private int id;
    private Trip trip;
    private LocalDate date;

    List<Place> places;

    public DateTrip(Trip trip, LocalDate date) {
        this(0, trip, date, null);
    }
}
