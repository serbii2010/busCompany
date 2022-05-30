package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
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
