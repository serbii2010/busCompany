package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripSchedule {
    private int id;
    private Trip trip;
    private Schedule schedule;

    public TripSchedule(Trip trip) {
        this(0, trip, trip.getSchedule());
    }
}
