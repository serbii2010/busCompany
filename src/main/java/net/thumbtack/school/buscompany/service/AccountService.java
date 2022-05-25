package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.account.AccountDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.account.UserTypeDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountDaoImpl accountDao;
    @Autowired
    private UserTypeDaoImpl userTypeDao;

    private final BidiMap<Account, UUID> authUsers = new DualHashBidiMap<>();


    public Account registrationAdmin(Account account) {
        UserType userType = userTypeDao.findByType(UserTypeEnum.ADMIN);
        account.setUserType(userType);
        account.setPassword(convertToMd5(account.getPassword()));
        return accountDao.insert(account);
    }

    public Account registrationClient(Account account) {
        UserType userType = userTypeDao.findByType(UserTypeEnum.CLIENT);
        account.setUserType(userType);
        account.setPassword(convertToMd5(account.getPassword()));
        return accountDao.insert(account);
    }

    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    public void deleteAccount(Account account) {
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
        Account account = authUsers.getKey(UUID.fromString(uuid));
        if (account == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
        return account;
    }

    public List<Account> getClients() throws ServerException {
        return accountDao.findByUserType(userTypeDao.findByType(UserTypeEnum.CLIENT).getId());
    }

    public void login(Account user, HttpServletResponse response) {
        if(user.getId() == 0) {
            return;
        }
        UUID uuid = UUID.randomUUID();
        UUID result = authUsers.putIfAbsent(user, uuid);
        if (result == null) {
            result = uuid;
        }
        Cookie cookie = new Cookie("JAVASESSIONID", result.toString());
        response.addCookie(cookie);
    }

    public void logout(String javaSessionId) {
        UUID uuid = UUID.fromString(javaSessionId);
        if (!authUsers.containsValue(uuid)) {
            return;
        }
        LOGGER.debug(String.format("User {} logout", authUsers.getKey(uuid).getLogin()));
        authUsers.removeValue(uuid);
    }

    public void checkPassword(Account account, String password) throws ServerException {
        if (!account.getPassword().equals(convertToMd5(password))) {
            throw new ServerException(ServerErrorCode.BAD_PASSWORD);
        }
    }

    public void checkIfAdmin(Account account) throws ServerException {
        if (!account.getUserType().getType().equals(UserTypeEnum.ADMIN.getType())) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public void setPassword(Account account, String newPassword) {
        account.setPassword(convertToMd5(newPassword));
    }

    private String convertToMd5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }
}
