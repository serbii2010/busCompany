package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
