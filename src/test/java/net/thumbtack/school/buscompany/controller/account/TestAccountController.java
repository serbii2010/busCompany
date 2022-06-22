package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {AccountController.class, AccountHelper.class})
class TestAccountController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AccountHelper accountHelper;

    @MockBean
    private AccountService accountService;

    private Admin admin;
    private Client client;
    private Cookie cookie;

    @BeforeEach
    public void init() {
        accountHelper.init();
        admin = accountHelper.getAdmin();
        client = accountHelper.getClient();
        cookie = accountHelper.getCookie();
    }

    @Test
    public void testGetInfo_notAuth() throws Exception {
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetInfo_authAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(admin);
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInfo_authClient() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(client);
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(client);
        mvc.perform(delete("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAccount_noAuth() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(client);
        mvc.perform(delete("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}