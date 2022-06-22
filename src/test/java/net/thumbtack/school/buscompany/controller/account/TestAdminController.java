package net.thumbtack.school.buscompany.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {AdminController.class, AccountHelper.class})
class TestAdminController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
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
    public void testInsertAdmin() throws Exception {
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
    public void testInsertAdmin_badFirstName() throws Exception {
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
    public void testInsertAdmin_badLastNAme() throws Exception {
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
    public void testInsertAdmin_badLengthFirstName() throws Exception {
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
    public void testInsertAdmin_badLengthLastName() throws Exception {
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
    public void testInsertAdmin_badLengthPatronymic() throws Exception {
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
    public void testInsertAdmin_badLengthPassword() throws Exception {
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
    public void testUpdateAdmin() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAdmin_badFirstName() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имяABC",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAdmin_badLastName() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилияABC",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAdmin_badPatronymic() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчествоABC",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAdmin_badCookie() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAdmin_badPassword() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                "password2"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        Mockito.doThrow(new ServerException(ServerErrorCode.BAD_PASSWORD)).when(accountService).checkPassword(admin, "password");
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAdmin_badLengthNewPassword() throws Exception {
        EditAdministratorDtoRequest request = new EditAdministratorDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "password",
                // 7 символов
                "passwor"
        );
        Mockito.when(accountService.getAuthAccount(cookie.getValue())).thenReturn(admin);
        Mockito.when(accountService.findAdmin(admin)).thenReturn(admin);
        mvc.perform(put("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andExpect(status().isBadRequest());
    }
}