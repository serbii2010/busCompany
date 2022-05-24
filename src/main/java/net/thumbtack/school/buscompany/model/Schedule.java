package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private int id;
    private String fromDate;
    private String toDate;
    private String periods;

    private List<Trip> trips;
}
