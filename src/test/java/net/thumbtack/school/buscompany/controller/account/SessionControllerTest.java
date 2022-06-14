package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.TestBaseAccount;
import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SessionController.class)
class SessionControllerTest extends TestBaseAccount {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;
    @MockBean
    protected AccountService accountService;

    @Test
    void testLogin() throws Exception {
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

    @Test
    void testLogin_badLogin() throws Exception {
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

    @Test
    void testLogin_badPassword() throws Exception {
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
    void testLogout() throws Exception {
        mvc.perform(delete("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testLogout_withoutCookie() throws Exception {
        mvc.perform(delete("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}