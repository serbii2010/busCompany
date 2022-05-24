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
}
