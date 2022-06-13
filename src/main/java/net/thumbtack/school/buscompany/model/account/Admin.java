package net.thumbtack.school.buscompany.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends Account {
    private String position;
    private int accountId;

    public Admin(String login, String password, String firstName, String lastName, String patronymic,
                 UserTypeEnum userType, String position, int accountId) {
        super(1, login, password, firstName, lastName, patronymic, userType);
        this.position = position;
        this.accountId = accountId;
    }
}
