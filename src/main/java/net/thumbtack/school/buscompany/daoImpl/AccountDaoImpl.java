package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.model.Account;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDaoImpl extends DaoImplBase implements Dao<Account> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    @Override
    public Account findById(String id) {

        return null;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account insert(Account account) {
        LOGGER.debug("DAO insert Account {}", account);
        try (SqlSession sqlSession = getSession()) {
            try {
                getAccountMapper(sqlSession).insert(account);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert account {} {}", account, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return account;
    }

    @Override
    public void remove(Account account) {
        LOGGER.debug("DAO delete Account {}", account);
        try (SqlSession sqlSession = getSession()) {
            try {
                getAccountMapper(sqlSession).delete(account);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete account {} {}", account, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    public Account findByLogin(String login) {
        LOGGER.debug("DAO get Account {}", login);
        try (SqlSession sqlSession = getSession()) {
            return getAccountMapper(sqlSession).getByLogin(login);
        } catch (RuntimeException ex) {
            LOGGER.info("not found account {} {}", login, ex);
            throw ex;
        }
    }
}
