package net.thumbtack.school.buscompany.controller.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class TestAccountController extends TestBaseAccount {

    @Test
    void testGetInfo_notAuth() throws Exception {
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetInfo_authAdmin() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(authAdmin);
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInfo_authClient() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(authClient);
        mvc.perform(get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }


    @Test
    void testDeleteAccount() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(authClient);
        mvc.perform(delete("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAccount_noAuth() throws Exception {
        Mockito.when(accountService.getAuthAccount("sessionId")).thenReturn(authClient);
        mvc.perform(delete("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}