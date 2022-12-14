package net.thumbtack.school.buscompany.service.account;

import net.thumbtack.school.buscompany.daoImpl.account.AccountDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.account.SessionDaoImpl;
import net.thumbtack.school.buscompany.dto.request.account.*;
import net.thumbtack.school.buscompany.dto.response.account.*;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.account.AdminMapper;
import net.thumbtack.school.buscompany.mappers.dto.account.ClientMapper;
import net.thumbtack.school.buscompany.model.Session;
import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountDaoImpl accountDao;
    @Autowired
    private SessionDaoImpl sessionDao;

    @Value("${user_idle_timeout}")
    private int userIdleTimeout;

    public RegistrationAdminDtoResponse registrationAdmin(RegistrationAdminDtoRequest request, HttpServletResponse response) throws ServerException {
        Admin admin = AdminMapper.INSTANCE.registrationAdminDtoToAccount(request);
        admin.setUserType(UserType.ADMIN);
        admin.setPassword(convertToMd5(admin.getPassword()));

        accountDao.insert(admin);
        LOGGER.debug("administrator registered");
        Session session = openSession(admin.getLogin());
        response.addCookie(new Cookie("JAVASESSIONID", session.getSessionId()));
        return AdminMapper.INSTANCE.accountToDto(admin);
    }

    public RegistrationClientDtoResponse registrationClient(RegistrationClientDtoRequest request, HttpServletResponse response) throws ServerException {
        Client client = ClientMapper.INSTANCE.registrationDtoToAccount(request);
        client.setUserType(UserType.CLIENT);
        client.setPassword(convertToMd5(client.getPassword()));
        accountDao.insert(client);
        LOGGER.debug("client registered");
        Session session = openSession(client.getLogin());
        response.addCookie(new Cookie("JAVASESSIONID", session.getSessionId()));
        return ClientMapper.INSTANCE.accountToDto(client);
    }

    public BaseAccountInfoDtoResponse getInfo(String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);

        switch (account.getUserType()) {
            case ADMIN:
                return AdminMapper.INSTANCE.accountToDtoInfo(findAdmin(account));
            case CLIENT:
                return ClientMapper.INSTANCE.accountToDtoInfo(findClient(account));
            default:
                throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
    }

    public List<InfoClientDtoResponse> getClients(String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        checkAdmin(account);

        List<InfoClientDtoResponse> result = new ArrayList<>();

        List<Client> accountsClient = accountDao.findClients();
        for (Account acc : accountsClient) {
            result.add(ClientMapper.INSTANCE.accountToDtoInfo(findClient(acc)));
        }
        return result;
    }

    public EditAdministratorDtoResponse updateAdmin(EditAdministratorDtoRequest request, String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        checkAdmin(account);

        Admin admin = findAdmin(account);

        AdminMapper.INSTANCE.update(admin, request, this);
        updateAccount(admin);
        LOGGER.debug("admin updated");
        return AdminMapper.INSTANCE.accountEditToDto(admin);
    }

    public EditClientDtoResponse updateClient(EditClientDtoRequest request, String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        checkClient(account);

        Client client = findClient(getAuthAccount(javaSessionId));
        ClientMapper.INSTANCE.update(client, request, this);
        updateAccount(client);
        LOGGER.debug("client updated");
        return ClientMapper.INSTANCE.accountEditToDto(client);
    }

    public EmptyDtoResponse deleteAccount(String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        logout(javaSessionId);
        deleteAccount(account);
        return new EmptyDtoResponse();
    }

    public void updateAccount(Account account) throws ServerException {
        accountDao.update(account);
    }

    public void deleteAccount(Account account) throws ServerException {
        if (account.getUserType() == UserType.ADMIN
                && accountDao.getCountAdmins() == 1) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
        accountDao.remove(account);
    }

    public Account getAccountByLogin(String login) throws ServerException {
        Account result = accountDao.findByLogin(login);
        if (result == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        return result;
    }

    public Account getAuthAccount(String uuid) throws ServerException {
        Session session = getSession(uuid);
        if (session == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
        Account account = session.getAccount();
        if (account == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
        return account;
    }

    public Admin findAdmin(Account account) throws ServerException {
        return accountDao.findAdmin(account);
    }

    public Client findClient(Account account) throws ServerException {
        return accountDao.findClient(account);
    }

    public BaseAccountDtoResponse login(LoginDtoRequest request, HttpServletResponse response)
            throws ServerException {
        Account account = getAccountByLogin(request.getLogin());
        checkDelete(account);
        checkPassword(account, request.getPassword());

        Session session = openSession(request.getLogin());

        response.addCookie(new Cookie("JAVASESSIONID", session.getSessionId()));

        switch (account.getUserType()) {
            case ADMIN: {
                LOGGER.debug("admin log in");
                return AdminMapper.INSTANCE.accountToDto(findAdmin(account));
            }
            case CLIENT: {
                LOGGER.debug("client log in");
                return ClientMapper.INSTANCE.accountToDto(findClient(account));
            }
        }
        throw new ServerException(ServerErrorCode.USER_TYPE_ERROR);
    }

    private void checkDelete(Account account) throws ServerException {
        if (account.getUserType() == UserType.CLIENT && accountDao.findClient(account) != null) {
            return;
        }
        if (account.getUserType() == UserType.ADMIN && accountDao.findAdmin(account) != null) {
            return;
        }
        throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
    }

    public void logout(String javaSessionId) throws ServerException {
        Session session = sessionDao.findBySessionId(javaSessionId);
        if (session != null) {
            sessionDao.remove(session);
        }
    }

    public Session getSession(String sessionId) throws ServerException {
        Session session = sessionDao.findBySessionId(sessionId);
        if (session != null) {
            if ((session.getLastAction().plusMinutes(userIdleTimeout).compareTo(LocalDateTime.now())) >= 0) {
                session.setLastAction(LocalDateTime.now());
                sessionDao.update(session);
                return session;
            }
            sessionDao.remove(session);
        }
        throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
    }

    public void checkPassword(Account account, String password) throws ServerException {
        if (!account.getPassword().equals(convertToMd5(password))) {
            throw new ServerException(ServerErrorCode.BAD_PASSWORD);
        }
    }

    public void checkAdmin(Account account) throws ServerException {
        if (!isAdmin(account)) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public void checkClient(Account account) throws ServerException {
        if (!isClient(account)) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public boolean isAdmin(Account account) throws ServerException {
        return account.getUserType().getType().equals(UserType.ADMIN.getType());
    }

    public boolean isClient(Account account) throws ServerException {
        return account.getUserType().getType().equals(UserType.CLIENT.getType());
    }

    public void setPassword(Account account, String newPassword) {
        account.setPassword(convertToMd5(newPassword));
    }

    private String convertToMd5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    private Session openSession(String login) throws ServerException {
        Account account = accountDao.findByLogin(login);
        if (account == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        Session session = sessionDao.findByAccountId(account.getId());
        if (session == null) {
            session = new Session(account);
            sessionDao.insert(session);
            return session;
        }
        session.setLastAction(LocalDateTime.now());
        sessionDao.update(session);
        return session;
    }
}
