package net.thumbtack.school.buscompany.service.account;

import net.thumbtack.school.buscompany.daoImpl.account.AccountDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.account.SessionDaoImpl;
import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.LoginClientDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationAdminDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.request.account.RegistrationClientDtoRequestHelper;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TestAccountService {

    @Autowired
    private AccountHelper accountHelper;

    @Mock
    private AccountDaoImpl accountDao;
    @Mock
    private SessionDaoImpl sessionDao;
    @InjectMocks
    private AccountService accountService;

    private Admin admin;
    private Client client;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(accountService, "userIdleTimeout", 30);
        accountHelper.init();
        admin = accountHelper.getAdmin();
        client = accountHelper.getClient();
    }

    @Test
    public void testRegistrationAdmin() throws Exception {
        Mockito.when(accountDao.insert(admin)).thenReturn(admin);
        Mockito.when(accountDao.findByLogin("admin")).thenReturn(admin);
        MockHttpServletResponse response = new MockHttpServletResponse();
        RegistrationAdminDtoRequest request = RegistrationAdminDtoRequestHelper.get("admin");
        RegistrationAdminDtoResponse registrationAdminDtoResponse = accountService.registrationAdmin(request, response);
        assertAll(
                "admin",
                () -> assertEquals("имя", registrationAdminDtoResponse.getFirstName()),
                () -> assertEquals(UserType.ADMIN.getType(), registrationAdminDtoResponse.getUserType())
        );
    }

    @Test
    public void testRegistrationClient() throws Exception {
        Mockito.when(accountDao.insert(client)).thenReturn(client);
        Mockito.when(accountDao.findByLogin("client")).thenReturn(client);
        MockHttpServletResponse response = new MockHttpServletResponse();
        RegistrationClientDtoRequest registrationClientDtoRequest = RegistrationClientDtoRequestHelper.get();
        RegistrationClientDtoResponse newClient = accountService.registrationClient(registrationClientDtoRequest, response);
        assertAll(
                "client",
                () -> assertEquals("имя-клиента", newClient.getFirstName()),
                () -> assertEquals(UserType.CLIENT.getType(), newClient.getUserType())
        );
    }

    @Test
    public void testDeleteAccount() throws Exception {
        Mockito.when(accountDao.getCountAdmins()).thenReturn(2);
        assertDoesNotThrow(() -> accountService.deleteAccount(admin));
    }

    @Test
    public void testDeleteAccount_badDeleteAdmin() throws Exception {
        Mockito.when(accountDao.getCountAdmins()).thenReturn(1);
        assertThrows(ServerException.class, () -> accountService.deleteAccount(admin));
    }

    @Test
    public void testGetAccountByLogin() throws Exception {
        Mockito.when(accountDao.findByLogin("admin")).thenReturn(admin);
        assertEquals(admin, accountService.getAccountByLogin("admin"));
    }

    @Test
    public void testGetAccountByLogin_badLogin() throws Exception {
        Mockito.when(accountDao.findByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
        assertThrows(ServerException.class, () -> accountService.getAccountByLogin("admin"));
    }

    @Test
    public void testGetAuthAccount() throws Exception {
        String sessionId = accountHelper.getCookie().getValue();
        Mockito.when(sessionDao.findBySessionId(sessionId)).thenReturn(accountHelper.getSession());

        assertEquals(admin, accountService.getAuthAccount(sessionId));
    }

    @Test
    public void testGetAuthAccount_notFound() {
        assertThrows(ServerException.class, () -> accountService.getAuthAccount(UUID.randomUUID().toString()));
    }

    @Test
    public void testLogin() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        LoginDtoRequest request = LoginClientDtoRequestHelper.get();
        Account client = accountHelper.getSessionClient().getAccount();
        client.setPassword(DigestUtils.md5DigestAsHex("password".getBytes()));
        Mockito.when(accountDao.findByLogin(request.getLogin())).thenReturn(client);
        Mockito.when(accountDao.findClient(client)).thenReturn(this.client);

        accountService.login(request, response);

        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
        assertAll(
                "cookie",
                () -> assertNotNull(cookieSessionId),
                () -> {
                    assert cookieSessionId != null;
                    assertNotEquals(accountHelper.getSessionClient().getSessionId(), cookieSessionId.getValue());
                }
        );
    }

    @Test
    public void testLogin_badLogin() throws Exception {
        Mockito.when(accountDao.findByLogin("admin")).thenReturn(null);
        MockHttpServletResponse response = new MockHttpServletResponse();
        LoginDtoRequest request = new LoginDtoRequest(
                "admin",
                "password"
        );

        assertThrows(ServerException.class, () -> accountService.login(request, response));
    }

    @Test
    public void testLogin_badPassword() throws Exception {
        Mockito.when(accountDao.findByLogin("admin")).thenReturn(admin);
        MockHttpServletResponse response = new MockHttpServletResponse();
        LoginDtoRequest request = new LoginDtoRequest(
                "admin",
                "password"
        );

        assertThrows(ServerException.class, () -> accountService.login(request, response));
    }

    @Test
    public void testLogout() throws Exception {
        String sessionId = accountHelper.getSessionClient().getSessionId();
        Mockito.when(sessionDao.findBySessionId(sessionId)).thenReturn(accountHelper.getSessionClient());
        Mockito.doNothing().when(sessionDao).remove(accountHelper.getSessionClient());
        assertDoesNotThrow(() -> accountService.logout(sessionId));
    }

    @Test
    public void testCheckPassword_admin() {
        admin.setPassword(DigestUtils.md5DigestAsHex("password".getBytes()));
        assertDoesNotThrow(() -> accountService.checkPassword(admin, "password"));
    }

    @Test
    public void testCheckPassword_badPasswordAdmin() {
        assertThrows(ServerException.class, () -> accountService.checkPassword(admin, "password2"));
    }

    @Test
    public void testCheckPassword_client() {
        client.setPassword(DigestUtils.md5DigestAsHex("password".getBytes()));
        assertDoesNotThrow(() -> accountService.checkPassword(client, "password"));
    }

    @Test
    public void testCheckPassword_badPasswordClient() {
        assertThrows(ServerException.class, () -> accountService.checkPassword(client, "password2"));
    }

    @Test
    public void testCheckAdmin() throws Exception {
        String cookieSessionId = accountHelper.getSession().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSession());
        assertDoesNotThrow(() -> accountService.checkAdmin(cookieSessionId));
    }

    @Test
    public void testCheckAdmin_bad() throws Exception {
        String cookieSessionId = accountHelper.getSessionClient().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSessionClient());
        assertThrows(ServerException.class, () -> accountService.checkAdmin(cookieSessionId));
    }

    @Test
    public void testCheckClient() throws Exception {
        String cookieSessionId = accountHelper.getSessionClient().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSessionClient());
        assertDoesNotThrow(() -> accountService.checkClient(cookieSessionId));
    }

    @Test
    public void testCheckClientBad() throws Exception {
        String cookieSessionId = accountHelper.getSession().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSession());
        assertThrows(ServerException.class, () -> accountService.checkClient(cookieSessionId));
    }

    @Test
    public void testIsAdmin() throws Exception {
        String cookieSessionId = accountHelper.getSession().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSession());
        assertTrue(accountService.isAdmin(cookieSessionId));
    }

    @Test
    public void testIsAdmin_bad() throws Exception {
        String cookieSessionId = accountHelper.getSessionClient().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSessionClient());
        assertFalse(accountService.isAdmin(cookieSessionId));
    }

    @Test
    public void testIsClient() throws Exception {
        String cookieSessionId = accountHelper.getSessionClient().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSessionClient());
        assertTrue(accountService.isClient(cookieSessionId));
    }

    @Test
    public void testIsClient_bad() throws Exception {
        String cookieSessionId = accountHelper.getSession().getSessionId();
        Mockito.when(sessionDao.findBySessionId(cookieSessionId)).thenReturn(accountHelper.getSession());
        assertFalse(accountService.isClient(cookieSessionId));
    }
}