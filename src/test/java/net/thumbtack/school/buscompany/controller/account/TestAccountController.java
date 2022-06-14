package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.TestBaseAccount;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class TestAccountController extends TestBaseAccount {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;
    @MockBean
    protected AccountService accountService;

    @Test
    void testGetInfo_notAuth() throws Exception {
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetInfo_authAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(admin);
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInfo_authClient() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(client);
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }


    @Test
    void testDeleteAccount() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(client);
        mvc.perform(delete("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAccount_noAuth() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(client);
        mvc.perform(delete("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}