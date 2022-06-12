package net.thumbtack.school.buscompany.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Account {
    private String email;
    private String phone;
    private int accountId;
}
