package net.thumbtack.school.buscompany.service;

import net.thumbtack.school.buscompany.daoImpl.AccountDaoImpl;
import net.thumbtack.school.buscompany.daoImpl.UserTypeDaoImpl;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService {
    @Autowired
    private AccountDaoImpl accountDao;
    @Autowired
    private UserTypeDaoImpl userTypeDao;

    public Account registrationAdmin(Account account) {
        UserType userType = userTypeDao.findByType(UserTypeEnum.ADMIN);
        account.setUserType(userType.getId());
        return accountDao.insert(account);
    }

    public Account registrationClient(Account account) {
        UserType userType = userTypeDao.findByType(UserTypeEnum.CLIENT);
        account.setUserType(userType.getId());
        return accountDao.insert(account);
    }
}
