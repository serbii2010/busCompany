package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    private int id;
    private int number;
    private Passenger passenger;
    private DateTrip dateTrip;

    public Place(int number, DateTrip dateTrip) {
        this(0, number, null, dateTrip);
    }

    public Place(int number, DateTrip dateTrip, Passenger passenger) {
        this(0, number, passenger, dateTrip);
    }
}
