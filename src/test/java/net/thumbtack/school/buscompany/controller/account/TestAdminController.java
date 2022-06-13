package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminController.class)
class TestAdminController extends TestBaseAccount {
    @Test
    void testInsertAdmin() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "admin",
                "password"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testInsertAdmin_badFirstName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                "name",
                "фамилия",
                "отчество",
                "director",
                "admin",
                "password"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertAdmin_badLastNAme() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                "имя",
                "lastname",
                "отчество",
                "director",
                "admin",
                "password"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertAdmin_badLengthFirstName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                // 51 символ
                "имяимяяяяяяяяяяяяяяяяяяяя-имяимяяяяяяяяяяяяяяяяяяяя",
                "фамилия",
                "отчество",
                "director",
                "admin",
                "password"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertAdmin_badLengthLastName() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                "имя-имяимяяяяяяяяяяяяяяяяяяяя",
                // 51 символ
                "имяимяяяяяяяяяяяяяяяяяяяя-имяимяяяяяяяяяяяяяяяяяяяя",
                "отчество",
                "director",
                "admin",
                "password"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertAdmin_badLengthPatronymic() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                // 51 символ
                "имя",
                "фамилия",// 51 символ
                "имяимяяяяяяяяяяяяяяяяяяяя-имяимяяяяяяяяяяяяяяяяяяяя",
                "director",
                "admin",
                "password"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertAdmin_badLengthPassword() throws Exception {
        Mockito.when(accountService.getAccountByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        RegistrationAdminDtoRequest request = new RegistrationAdminDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "admin",
                // 7 символов
                "passwor"
        );
        mvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAdmin() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateAdmin_badFirstName() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имяABC",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAdmin_badLastName() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилияABC",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAdmin_badPatronymic() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчествоABC",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testUpdateAdmin_badCookie() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAdmin_badPassword() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        Mockito.doThrow(new ServerException(ServerErrorCode.BAD_PASSWORD)).when(accountService).checkPassword(authAdmin, "password");
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testUpdateAdmin_badLengthNewPassword() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                // 7 символов
                "passwor"
        );
        Mockito.when(accountService.findAdmin(null)).thenReturn(authAdmin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }
}