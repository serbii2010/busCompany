package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
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
