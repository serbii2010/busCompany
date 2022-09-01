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
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
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
        admin.setUserType(UserTypeEnum.ADMIN);
        admin.setPassword(convertToMd5(admin.getPassword()));
        // REVU не будет трансакции
        // если getSessionByLogin провалится, аккаунт так и останется
        // либо делать все в одном методе dao
        // либо подключить mybatis-spring
        // и тогда можно будет использовать в сервисе @Transactional
        accountDao.insert(admin);
        LOGGER.debug("administrator registered");
        // REVU зачем Вы создали LoginDtoRequest, он Вам тут совсем не нужен
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(admin.getLogin(), admin.getPassword());
        Session session = getSessionByLogin(loginDtoRequest.getLogin());
        response.addCookie(new Cookie("JAVASESSIONID", session.getSessionId()));
        return AdminMapper.INSTANCE.accountToDto(admin);
    }

    public RegistrationClientDtoResponse registrationClient(RegistrationClientDtoRequest request, HttpServletResponse response) throws ServerException {
        Client client = ClientMapper.INSTANCE.registrationDtoToAccount(request);
        client.setUserType(UserTypeEnum.CLIENT);
        client.setPassword(convertToMd5(client.getPassword()));
        accountDao.insert(client);
        LOGGER.debug("client registered");
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(client.getLogin(), client.getPassword());
        Session session = getSessionByLogin(loginDtoRequest.getLogin());
        response.addCookie(new Cookie("JAVASESSIONID", session.getSessionId()));
        return ClientMapper.INSTANCE.accountToDto(client);
    }

    public BaseAccountInfoDtoResponse getInfo(String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);

        if (account.getUserType() == UserTypeEnum.CLIENT) {
            return ClientMapper.INSTANCE.accountToDtoInfo(findClient(account));
            // REVU else не нужен
            // а вообще-то лучше switch
        } else if (account.getUserType() == UserTypeEnum.ADMIN) {
            return AdminMapper.INSTANCE.accountToDtoInfo(findAdmin(account));
        } else {
            throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
    }

    public List<InfoClientDtoResponse> getClients() throws ServerException {
        List<InfoClientDtoResponse> result = new ArrayList<>();

        List<Client> accountsClient = accountDao.findClients();
        for (Account acc : accountsClient) {
            result.add(ClientMapper.INSTANCE.accountToDtoInfo(findClient(acc)));
        }
        return result;
    }

    public EditAdministratorDtoResponse updateAdmin(EditAdministratorDtoRequest request, String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        if (account.getUserType() != UserTypeEnum.ADMIN) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
        Admin admin = findAdmin(account);

        AdminMapper.INSTANCE.update(admin, request, this);
        updateAccount(admin);
        LOGGER.debug("admin updated");
        return AdminMapper.INSTANCE.accountEditToDto(admin);
    }

    public EditClientDtoResponse updateClient(EditClientDtoRequest request, String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        if (account.getUserType() != UserTypeEnum.CLIENT) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
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

    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    public void deleteAccount(Account account) throws ServerException {
        if (account.getUserType() == UserTypeEnum.ADMIN
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

        Session session = getSessionByLogin(request.getLogin());

        response.addCookie(new Cookie("JAVASESSIONID", session.getSessionId()));
        if (account.getUserType() == UserTypeEnum.ADMIN) {
            LOGGER.debug("admin log in");
            return AdminMapper.INSTANCE.accountToDto(findAdmin(account));
        } else {
            LOGGER.debug("client log in");
            return ClientMapper.INSTANCE.accountToDto(findClient(account));
        }
    }

    private void checkDelete(Account account) throws ServerException {
        if (account.getUserType() == UserTypeEnum.CLIENT && accountDao.findClient(account) != null) {
            return;
        }
        if (account.getUserType() == UserTypeEnum.ADMIN && accountDao.findAdmin(account) != null) {
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

    public void checkAdmin(String javaSessionId) throws ServerException {
        if (!isAdmin(javaSessionId)) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public void checkClient(String javaSessionId) throws ServerException {
        if (!isClient(javaSessionId)) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public boolean isAdmin(String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        return account.getUserType().getType().equals(UserTypeEnum.ADMIN.getType());
    }

    public boolean isClient(String javaSessionId) throws ServerException {
        Account account = getAuthAccount(javaSessionId);
        return account.getUserType().getType().equals(UserTypeEnum.CLIENT.getType());
    }

    public void setPassword(Account account, String newPassword) {
        account.setPassword(convertToMd5(newPassword));
    }

    private String convertToMd5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    // REVU смените имя. get методы ничего не создают
    // insertSession или просто login
    private Session getSessionByLogin(String login) throws ServerException {
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
