package net.thumbtack.school.buscompany.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountHelper {
    private Admin admin;
    private Client client;
    private Cookie cookie;

    public void init() {
        this.admin = new Admin(
                "login",
                "password",
                "first",
                "last",
                "patronymic",
                UserTypeEnum.ADMIN,
                "тестировщик",
                1);

        this.client = new Client(
                "login",
                "password",
                "first",
                "last",
                "patronymic",
                UserTypeEnum.CLIENT,
                "тестировщик",
                "88005553535",
                1);

        this.cookie = new Cookie("JAVASESSIONID", "sessionId");
    }

}
