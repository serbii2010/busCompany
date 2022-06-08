package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private int id;
    private OrderPassenger orderPassenger;
    private int place;

    public Ticket(OrderPassenger orderPassenger, int place) {
        this(0, orderPassenger, place);
    }
}
