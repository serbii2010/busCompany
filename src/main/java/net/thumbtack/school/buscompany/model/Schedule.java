package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private int id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String period;

    private List<Trip> trips;
}
