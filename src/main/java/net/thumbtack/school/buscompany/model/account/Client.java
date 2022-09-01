package net.thumbtack.school.buscompany.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Account {
    private String email;
    private String phone;
    private int accountId;

    public Client(String login, String password, String firstName, String lastName, String patronymic,
                  UserType userType, String email, String phone, int accountId) {
        super(1, login, password, firstName, lastName, patronymic, userType);
        this.email = email;
        this.phone = phone;
        this.accountId = accountId;
    }
}
