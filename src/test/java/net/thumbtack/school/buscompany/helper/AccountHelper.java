package net.thumbtack.school.buscompany.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationClientDtoRequestHelper;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
                "a@a.a",
                "88005553535",
                1);

        this.cookie = new Cookie("JAVASESSIONID", "sessionId");
    }

    public static String registrationAdmin(MockMvc mvc, ObjectMapper mapper) throws Exception {
        RegistrationAdminDtoRequest request = RegistrationAdminDtoRequestHelper.get();

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

}
