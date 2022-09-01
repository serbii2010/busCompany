package net.thumbtack.school.buscompany.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.helper.dto.request.account.LoginAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.LoginClientDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationClientDtoRequestHelper;
import net.thumbtack.school.buscompany.model.Session;
import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountHelper {
    private Admin admin;
    private Client client;
    private Cookie cookie;
    private Session session;
    private Session sessionClient;

    public void init() {
        this.admin = new Admin(
                "login",
                "password",
                "first",
                "last",
                "patronymic",
                UserType.ADMIN,
                "тестировщик",
                1);

        this.client = new Client(
                "login",
                "password",
                "first",
                "last",
                "patronymic",
                UserType.CLIENT,
                "a@a.a",
                "88005553535",
                1);

        this.cookie = new Cookie("JAVASESSIONID", UUID.randomUUID().toString());

        this.session = new Session(1, admin, cookie.getValue(), LocalDateTime.now());
        this.sessionClient = new Session(1, client, cookie.getValue(), LocalDateTime.now());
    }

    public static String registrationAdmin(String login, MockMvc mvc, ObjectMapper mapper) throws Exception {
        RegistrationAdminDtoRequest request = RegistrationAdminDtoRequestHelper.get(login);

        MvcResult result = mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        return Objects.requireNonNull(result.getResponse().getCookie("JAVASESSIONID")).getValue();
    }

    public static String registrationClient(MockMvc mvc, ObjectMapper mapper) throws Exception {
        RegistrationClientDtoRequest request = RegistrationClientDtoRequestHelper.get();

        MvcResult result = mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        return Objects.requireNonNull(result.getResponse().getCookie("JAVASESSIONID")).getValue();
    }

    public static String loginAdmin(MockMvc mvc, ObjectMapper mapper) throws Exception {
        LoginDtoRequest request = LoginAdminDtoRequestHelper.get();

        return Objects.requireNonNull(mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getCookie("JAVASESSIONID"))
                .getValue();
    }

    public static String loginClient(MockMvc mvc, ObjectMapper mapper) throws Exception {
        LoginDtoRequest request = LoginClientDtoRequestHelper.get();

        return Objects.requireNonNull(mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getCookie("JAVASESSIONID"))
                .getValue();
    }

}
