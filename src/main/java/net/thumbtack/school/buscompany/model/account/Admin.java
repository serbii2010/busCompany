package net.thumbtack.school.buscompany.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends Account {
    private String position;
    private int accountId;
}
