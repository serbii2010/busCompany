package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.AccountDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.UserTypeDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AccountService {
    @Autowired
    private AccountDaoImpl accountDao;
    @Autowired
    private UserTypeDaoImpl userTypeDao;

    private Map<Account, UUID> authUsers = new HashMap<>();


    public Account registrationAdmin(Account account) {
        UserType userType = userTypeDao.findByType(UserTypeEnum.ADMIN);
        account.setUserType(userType.getId());
        account.setPassword(convertToMd5(account.getPassword()));
        return accountDao.insert(account);
    }

    public Account registrationClient(Account account) {
        UserType userType = userTypeDao.findByType(UserTypeEnum.CLIENT);
        account.setUserType(userType.getId());
        account.setPassword(convertToMd5(account.getPassword()));
        return accountDao.insert(account);
    }

    public Account getAccountByLogin(String login) throws ServerException {
        Account result = accountDao.findByLogin(login);
        if (result == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        return result;
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

    public void checkPassword(Account account, String password) throws ServerException {
        if (!account.getPassword().equals(convertToMd5(password))) {
            throw new ServerException(ServerErrorCode.BAD_PASSWORD);
        }
    }

    public int getUserTypeId(UserTypeEnum type) {
        return userTypeDao.findByType(type).getId();
    }

    private String convertToMd5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }
}
