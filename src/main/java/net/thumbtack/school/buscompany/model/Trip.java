package net.thumbtack.school.buscompany.model;

import lombok.*;

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
    private String start;
    private String duration;
    private int price;
    private boolean approved;
    private Schedule schedule;
    private List<DateTrip> dates;
}
