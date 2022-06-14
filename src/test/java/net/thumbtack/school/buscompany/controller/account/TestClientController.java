package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.TestBaseAccount;
import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
class TestClientController extends TestBaseAccount {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;
    @MockBean
    protected AccountService accountService;

    @Test
    public void testInsertClient() throws Exception {
        Mockito.when(accountService.getAccountByLogin("client")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationClientDtoRequest request = new RegistrationClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email@em.ail",
                "88005553535",
                "client",
                "password"
        );
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
                "имяW",
                "фамилия",
                "отчество",
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
                "имя",
                "фамилияW",
                "отчество",
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
                "имя",
                "фамилия",
                "отчество@",
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
                "имя",
                "фамилия",
                "отчество",
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
                "имя",
                "фамилия",
                "отчество",
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
                "имя",
                "фамилия",
                "отчество",
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
                "имяимяяяяяяяяяяяяяяяяяяяя-имяимяяяяяяяяяяяяяяяяяяяя",
                "фамилия",
                "отчество",
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
                "имя",
                "имяимяяяяяяяяяяяяяяяяяяяя-имяимяяяяяяяяяяяяяяяяяяяя",
                "отчество",
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
                "имя",
                "фамилия",
                "отчествоотчествоотчествоотчествоотчествоотчествоотчество",
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
                "имя",
                "фамилия",
                "отчество",
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
                "имя",
                "фамилия",
                "отчество",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
                "имя@",
                "фамилия",
                "отчество",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
                "имя",
                "фамилия@",
                "отчество",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
                "имя",
                "фамилия",
                "отчество@",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
                "имя",
                "фамилия",
                "отчество",
                "email",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
                "имя",
                "фамилия",
                "отчество",
                "email@email.com",
                "+88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
                "имя",
                "фамилия",
                "отчество",
                "email@email.com",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateClient_badMinLengthPassword() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email@email.com",
                "88005553535",
                "password",
                "passwor"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(client);
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
    public void testGetClients_badAuth() throws Exception {
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin("sessionId");
        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }
}