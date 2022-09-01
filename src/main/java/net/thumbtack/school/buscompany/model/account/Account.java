package net.thumbtack.school.buscompany.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.model.UserType;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private UserType userType;
}
