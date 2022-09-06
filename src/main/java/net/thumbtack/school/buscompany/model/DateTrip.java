package net.thumbtack.school.buscompany.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DateTrip {
    private int id;
    private Trip trip;
    private LocalDate date;
    private int freePlaces;

    List<Place> places;

    public DateTrip(Trip trip, LocalDate date) {
        this(0, trip, date, trip.getBus().getPlaceCount(), null);
    }
}
