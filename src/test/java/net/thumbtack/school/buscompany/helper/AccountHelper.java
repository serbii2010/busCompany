package net.thumbtack.school.buscompany.helper;

import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.Cookie;

public class AccountHelper {
    private static AccountHelper instance = null;

    private AccountHelper() {
        init();
    };

    public static AccountHelper getInstance() {
        if (instance == null) {
            instance = new AccountHelper();
        }
        return instance;
    }

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

    public Admin getAdmin() {
        return admin;
    }

    public Client getClient() {
        return client;
    }

    public Cookie getCookie() {
        return cookie;
    }

}
