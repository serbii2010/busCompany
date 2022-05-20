package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
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
    public Account findById(String id) throws ServerException {
        LOGGER.debug("DAO find Account {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getAccountMapper(sqlSession).getById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get account {} {}", id, ex);
                throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
            }
        }
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    public List<Account> findByUserType(Integer userType) throws ServerException {
        LOGGER.debug(String.format("DAO find all account by userType {}", userType));
        try (SqlSession sqlSession = getSession()){
            try {
                return getAccountMapper(sqlSession).getByUserType(userType);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get account {} {}", userType, ex);
                throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
            }
        }
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

    @Override
    public void update(Account account) {
        LOGGER.debug("DAO update Account {}", account);
        try (SqlSession sqlSession = getSession()) {
            try {
                getAccountMapper(sqlSession).update(account);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update account {} {}", account, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    public Account findByLogin(String login) throws ServerException {
        LOGGER.debug("DAO get Account {}", login);
        try (SqlSession sqlSession = getSession()) {
            return getAccountMapper(sqlSession).getByLogin(login);
        } catch (RuntimeException ex) {
            LOGGER.info("not found account {} {}", login, ex);
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
    }
}
