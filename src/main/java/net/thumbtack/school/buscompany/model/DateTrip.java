package net.thumbtack.school.buscompany.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateTrip {
    private int id;
    private Trip trip;
    private Date date;

    List<Place> places;

    public DateTrip(Trip trip, Date date) {
        this(0, trip, date, null);
    }
}
