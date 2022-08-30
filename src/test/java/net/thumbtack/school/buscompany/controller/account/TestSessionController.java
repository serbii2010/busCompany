package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {SessionController.class, AccountHelper.class})
class TestSessionController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AccountHelper accountHelper;

    @MockBean
    private AccountService accountService;

    private Admin admin;
    private Cookie cookie;

    @BeforeEach
    public void init() {
        accountHelper.init();
        admin = accountHelper.getAdmin();
        cookie = accountHelper.getCookie();
    }

    @Test
    public void testLogin() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenReturn(admin);
        LoginDtoRequest request = new LoginDtoRequest(
                "admin",
                "password"
        );
        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Disabled
    @Test
    public void testLogin_badLogin() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        LoginDtoRequest request = new LoginDtoRequest(
                "admin",
                "password"
        );
        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Disabled
    @Test
    public void testLogin_badPassword() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenReturn(admin);
        Mockito.doThrow(new ServerException(ServerErrorCode.BAD_PASSWORD)).when(accountService).checkPassword(admin, "password");
        LoginDtoRequest request = new LoginDtoRequest(
                "admin",
                "password"
        );
        mvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogout() throws Exception {
        mvc.perform(delete("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogout_withoutCookie() throws Exception {
        mvc.perform(delete("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}