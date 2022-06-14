package net.thumbtack.school.buscompany.service.account;

import net.thumbtack.school.buscompany.TestBaseAccount;
import net.thumbtack.school.buscompany.daoImpl.account.AccountDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestAccountService extends TestBaseAccount {

    @Mock
    private AccountDaoImpl accountDao = new AccountDaoImpl();
    @InjectMocks
    private AccountService accountService;

    @Test
    void testRegistrationAdmin() throws Exception {
        Mockito.when(accountDao.insert(admin)).thenReturn(admin);
        Admin newAdmin = accountService.registrationAdmin(admin);
        assertAll(
                "admin",
                () -> assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", newAdmin.getPassword()),
                () -> assertEquals(UserTypeEnum.ADMIN, newAdmin.getUserType())
        );
    }

    @Test
    void testRegistrationClient() {
        Mockito.when(accountDao.insert(client)).thenReturn(client);
        Client newClient = accountService.registrationClient(client);
        assertAll(
                "client",
                () -> assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", newClient.getPassword()),
                () -> assertEquals(UserTypeEnum.CLIENT, newClient.getUserType())
        );
    }

    @Test
    void testDeleteAccount() throws Exception {
        Mockito.when(accountDao.getCountAdmins()).thenReturn(2);
        assertDoesNotThrow(() -> accountService.deleteAccount(admin));
    }

    @Test
    void testDeleteAccount_badDeleteAdmin() throws Exception {
        Mockito.when(accountDao.getCountAdmins()).thenReturn(1);
        assertThrows(ServerException.class, () -> accountService.deleteAccount(admin));
    }

    @Test
    void testGetAccountByLogin() {
    }

    @Test
    void testGetAuthAccount() {
    }

    @Test
    void testFindAdmin() {
    }

    @Test
    void testFindClient() {
    }

    @Test
    void testGetClients() {
    }

    @Test
    void testLogin() {
    }

    @Test
    void testLogout() {
    }

    @Test
    void testCheckPassword() {
    }

    @Test
    void testCheckAdmin() {
    }

    @Test
    void testCheckClient() {
    }

    @Test
    void testIsAdmin() {
    }

    @Test
    void testIsClient() {
    }

    @Test
    void testIsAuth() {
    }

}