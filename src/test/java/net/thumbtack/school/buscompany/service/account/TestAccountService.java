//@todo
//package net.thumbtack.school.buscompany.service.account;
//
//import net.thumbtack.school.buscompany.helper.AccountHelper;
//import net.thumbtack.school.buscompany.daoImpl.account.AccountDaoImpl;
//import net.thumbtack.school.buscompany.exception.ServerErrorCode;
//import net.thumbtack.school.buscompany.exception.ServerException;
//import net.thumbtack.school.buscompany.model.account.Admin;
//import net.thumbtack.school.buscompany.model.account.Client;
//import net.thumbtack.school.buscompany.utils.UserTypeEnum;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//import javax.servlet.http.Cookie;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class TestAccountService {
//
//    @MockBean
//    private AccountHelper accountHelper;
//
//    @Mock
//    private AccountDaoImpl accountDao;
//    @InjectMocks
//    private AccountService accountService;
//
//    private Admin admin;
//    private Client client;
//
//    @BeforeEach
//    public void init() {
//        accountHelper.init();
//        admin = accountHelper.getAdmin();
//        client = accountHelper.getClient();
//    }
//
//    @Test
//    public void testRegistrationAdmin() {
//        Mockito.when(accountDao.insert(admin)).thenReturn(admin);
//        Admin newAdmin = accountService.registrationAdmin(admin);
//        assertAll(
//                "admin",
//                () -> assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", newAdmin.getPassword()),
//                () -> assertEquals(UserTypeEnum.ADMIN, newAdmin.getUserType())
//        );
//    }
//
//    @Test
//    public void testRegistrationClient() {
//        Mockito.when(accountDao.insert(client)).thenReturn(client);
//        Client newClient = accountService.registrationClient(client);
//        assertAll(
//                "client",
//                () -> assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", newClient.getPassword()),
//                () -> assertEquals(UserTypeEnum.CLIENT, newClient.getUserType())
//        );
//    }
//
//    @Test
//    public void testDeleteAccount() throws Exception {
//        Mockito.when(accountDao.getCountAdmins()).thenReturn(2);
//        assertDoesNotThrow(() -> accountService.deleteAccount(admin));
//    }
//
//    @Test
//    public void testDeleteAccount_badDeleteAdmin() throws Exception {
//        Mockito.when(accountDao.getCountAdmins()).thenReturn(1);
//        assertThrows(ServerException.class, () -> accountService.deleteAccount(admin));
//    }
//
//    @Test
//    public void testGetAccountByLogin() throws Exception {
//        Mockito.when(accountDao.findByLogin("admin")).thenReturn(admin);
//        assertEquals(admin, accountService.getAccountByLogin("admin"));
//    }
//
//    @Test
//    public void testGetAccountByLogin_badLogin() throws Exception {
//        Mockito.when(accountDao.findByLogin("admin")).thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));
//        assertThrows(ServerException.class, () -> accountService.getAccountByLogin("admin"));
//    }
//
//    @Test
//    public void testGetAuthAccount() throws Exception {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        String sessionId = response.getCookie("JAVASESSIONID").getValue();
//        assertEquals(admin, accountService.getAuthAccount(sessionId));
//    }
//
//    @Test
//    public void testGetAuthAccount_notFound() {
//        assertThrows(ServerException.class, () -> accountService.getAuthAccount(UUID.randomUUID().toString()));
//    }
//
//    @Test
//    public void testLogin() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertNotNull(cookieSessionId);
//    }
//
//    @Test
//    public void testLogout() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        accountService.logout(cookieSessionId.getValue());
//        assertThrows(ServerException.class, () -> accountService.getAuthAccount(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testCheckPassword_admin() {
//        Mockito.when(accountDao.insert(admin)).thenReturn(admin);
//        accountService.registrationAdmin(admin);
//        assertDoesNotThrow(() -> accountService.checkPassword(admin, "password"));
//    }
//
//    @Test
//    public void testCheckPassword_badPasswordAdmin() {
//        Mockito.when(accountDao.insert(admin)).thenReturn(admin);
//        accountService.registrationAdmin(admin);
//        assertThrows(ServerException.class, () -> accountService.checkPassword(admin, "password2"));
//    }
//
//    @Test
//    public void testCheckPassword_client() {
//        Mockito.when(accountDao.insert(client)).thenReturn(client);
//        accountService.registrationClient(client);
//        assertDoesNotThrow(() -> accountService.checkPassword(client, "password"));
//    }
//
//    @Test
//    public void testCheckPassword_badPasswordClient() {
//        Mockito.when(accountDao.insert(client)).thenReturn(client);
//        accountService.registrationClient(client);
//        assertThrows(ServerException.class, () -> accountService.checkPassword(client, "password2"));
//    }
//
//    @Test
//    public void testCheckAdmin() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertDoesNotThrow(() -> accountService.checkAdmin(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testCheckAdmin_bad() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(client, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertThrows(ServerException.class, () -> accountService.checkAdmin(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testCheckClient() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(client, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertDoesNotThrow(() -> accountService.checkClient(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testCheckClientBad() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertThrows(ServerException.class, () -> accountService.checkClient(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testIsAdmin() throws Exception {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertTrue(accountService.isAdmin(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testIsAdmin_bad() throws Exception {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(client, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertFalse(accountService.isAdmin(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testIsClient() throws Exception {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(client, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertTrue(accountService.isClient(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testIsClient_bad() throws Exception {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertFalse(accountService.isClient(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testIsAuth() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        Cookie cookieSessionId = response.getCookie("JAVASESSIONID");
//        assertTrue(accountService.isAuth(cookieSessionId.getValue()));
//    }
//
//    @Test
//    public void testIsAuth_bad() {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        accountService.login(admin, response);
//        assertFalse(accountService.isAuth(UUID.randomUUID().toString()));
//    }
//}