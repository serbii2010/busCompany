package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    private int id;
    private Bus bus;
    private Station fromStation;
    private Station toStation;
    private LocalTime start;
    private int duration;
    private int price;
    private boolean approved;
    private Schedule schedule;
    private List<DateTrip> dates;
}
