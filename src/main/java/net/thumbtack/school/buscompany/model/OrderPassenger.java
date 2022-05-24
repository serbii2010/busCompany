package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPassenger {
    private int id;
    private Orders order;
    private Passenger passenger;
}
