package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPassenger {
    private int id;
    private Order order;
    private Passenger passenger;

    public OrderPassenger(Order order, Passenger passenger) {
        this(0, order, passenger);
    }
}
