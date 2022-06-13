package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
class TestClientController extends TestBaseAccount {

    @Test
    void testInsertClient() throws Exception {
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
    void testInsertClient_badFirstName() throws Exception {
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
    void testInsertClient_badLastName() throws Exception {
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
    void testInsertClient_badPatronymic() throws Exception {
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
    void testInsertClientBadEmail() throws Exception {
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
    void testInsertClient_badPhone() throws Exception {
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
    void testInsertClient_badPasswordLength() throws Exception {
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
    void testInsertClient_badLengthFirstName() throws Exception {
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
    void testInsertClient_badLengthLastName() throws Exception {
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
    void testInsertClient_badLengthPatronymic() throws Exception {
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
    void testInsertClient_badMinLengthPassword() throws Exception {
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
    void testUpdateClient() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateClient_badFirstName() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя@",
                "фамилия",
                "отчество",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_badLastName() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия@",
                "отчество",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_badPatronymic() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество@",
                "email@em.ail",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_badEmail() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_badPhone() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email@email.com",
                "+88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_badCookie() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email@email.com",
                "88005553535",
                "password",
                "password2"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_badMinLengthPassword() throws Exception {
        EditClientDtoRequest request = new EditClientDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "email@email.com",
                "88005553535",
                "password",
                "passwor"
        );
        Mockito.when(accountService.findClient(null)).thenReturn(authClient);
        mvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetClients() throws Exception {
        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClients_badAuth() throws Exception {
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin("sessionId");
        mvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }
}