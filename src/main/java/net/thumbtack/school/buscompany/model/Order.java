package net.thumbtack.school.buscompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.model.account.Account;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private Trip trip;
    private Account account;
    private String date;

    private List<Passenger> passengers;
}
