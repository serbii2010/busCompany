package net.thumbtack.school.buscompany.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateTrip {
    private int id;
    private Trip trip;
    private Date date;

    public DateTrip(Trip trip, Date date) {
        this(0, trip, date);
    }
}
