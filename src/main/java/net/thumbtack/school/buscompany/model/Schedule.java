package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private int id;
    private Date fromDate;
    private Date toDate;
    private String period;

    private List<Trip> trips;
}
