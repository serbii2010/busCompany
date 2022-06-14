package net.thumbtack.school.buscompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

public class TestBaseAccount {

    protected Admin admin = new Admin(
            "login",
            "password",
            "first",
            "last",
            "patronymic",
            UserTypeEnum.ADMIN,
            "тестировщик",
            1);

    protected Client client = new Client(
            "login",
            "password",
            "first",
            "last",
            "patronymic",
            UserTypeEnum.CLIENT,
            "тестировщик",
            "88005553535",
            1);

    protected Cookie cookie = new Cookie("JAVASESSIONID","sessionId");
}
