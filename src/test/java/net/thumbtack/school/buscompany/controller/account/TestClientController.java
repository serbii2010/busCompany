package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ClientController.class, AccountHelper.class})
class TestClientController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AccountHelper accountHelper;

    @MockBean
    private AccountService accountService;

    private Client client;
    private Cookie cookie;

    @BeforeEach
    public void init() {
        accountHelper.init();
        client = accountHelper.getClient();
        cookie = accountHelper.getCookie();
    }

    @Test
    public void testInsertClient() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        MockHttpServletResponse response = new MockHttpServletResponse();
        Mockito.when(accountService.registrationClient(request, response)).thenCallRealMethod();
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testInsertClient_badFirstName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????W",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badLastName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????W",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badPatronymic() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????@",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClientBadEmail() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badPhone() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "+88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badPasswordLength() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "passwor"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badLengthFirstName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????????????????????????????????????????????????-??????????????????????????????????????????????????",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badLengthLastName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????????????????????????????????????????-??????????????????????????????????????????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badLengthPatronymic() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????????????????????????????????????????????????????????????????????????????????????????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInsertClient_badMinLengthPassword() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "client",
                "passwor"
        );
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateClient_badFirstName() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????@",
                "??????????????",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badLastName() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????@",
                "????????????????",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badPatronymic() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????@",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badEmail() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badPhone() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@email.com",
                "+88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badCookie() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@email.com",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badMinLengthPassword() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "??????",
                "??????????????",
                "????????????????",
                "email@email.com",
                "88005553535",
                "password",
                "passwor"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(client);
        Mockito.when(accountService.findClient(client)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetClients() throws Exception {
        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetClients_badAuthByAdmin() throws Exception {
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(accountHelper.getClient());
        Mockito.when(accountService.getClients(cookie.getValue())).thenCallRealMethod();
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(accountHelper.getClient());
        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }
}