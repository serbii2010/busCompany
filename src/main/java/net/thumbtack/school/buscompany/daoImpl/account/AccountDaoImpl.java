package net.thumbtack.school.buscompany.daoImpl.account;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
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

    public List<Client> findClients() throws ServerException {
        LOGGER.debug("DAO find all client");
        try (SqlSession sqlSession = getSession()){
            try {
                return getAccountMapper(sqlSession).getClients();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get client {}", ex);
                throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
            }
        }
    }

    @Override
    public Account insert(Account account) {
        // REVU а если такой логин уже есть ?
        // тест такой есть ?
        // тогда будет в ex.cause Mysqlintegrityconstraintviolationexception
        // и его надо ловить
        // альтернатива - подключить mybatis-spring
        // и тогда ловить
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/dao/DuplicateKeyException.html
        LOGGER.debug("DAO insert Account {}", account);
        try (SqlSession sqlSession = getSession()) {
            try {
                getAccountMapper(sqlSession).insert(account);
                int idAccount = account.getId();
                if (account.getClass() == Client.class) {
                    getClientMapper(sqlSession).insert((Client) account);
                } else if (account.getClass() == Admin.class) {
                    getAdminMapper(sqlSession).insert((Admin) account);
                }
                account.setId(idAccount);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert account {} {}", account, ex);
                sqlSession.rollback();
                // REVU лучше выбросить тут свой исключение ServerException с кодом DATABASE_EROOR
                // тогда в GlobalErrorHandler для этого случая сработает обработчик ServerException
                // а всякие неожиданные RuntimeException пусть идут в обработчик для Throwable
                // которого у СВас, кстати, нет
                // вот тут хороший список обработчиков
                // http://rsdn.org/forum/java/8264492.1
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
                if (account.getUserType() == UserTypeEnum.CLIENT) {
                    getClientMapper(sqlSession).deleteByAccount(account);
                } else if (account.getUserType() == UserTypeEnum.ADMIN) {
                    getAdminMapper(sqlSession).deleteByAccount(account);
                }
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
                if (account.getClass() == Client.class) {
                    Client client = (Client) account;
                    getAccountMapper(sqlSession).update(client, String.valueOf(client.getAccountId()));
                    getClientMapper(sqlSession).update(client);
                } else if (account.getClass() == Admin.class) {
                    Admin admin = (Admin) account;
                    getAccountMapper(sqlSession).update(admin, String.valueOf(admin.getAccountId()));
                    getAdminMapper(sqlSession).update(admin);
                }
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

    public Admin findAdmin(Account account) throws ServerException {
        LOGGER.debug("DAO get Admin {}", account);
        try (SqlSession sqlSession = getSession()) {
            return getAdminMapper(sqlSession).findAdmin(account);
        } catch (RuntimeException ex) {
            LOGGER.info("not found Admin {} {}", account, ex);
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
    }

    public Client findClient(Account account) throws ServerException {
        LOGGER.debug("DAO get Client {}", account);
        try (SqlSession sqlSession = getSession()) {
            return getClientMapper(sqlSession).findClient(account);
        } catch (RuntimeException ex) {
            LOGGER.info("not found Client {} {}", account, ex);
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
    }

    public int getCountAdmins() throws ServerException {
        LOGGER.debug("DAO get count Admins");
        try (SqlSession sqlSession = getSession()) {
            return getAdminMapper(sqlSession).getCount();
        } catch (RuntimeException ex) {
            LOGGER.info("not found Admin");
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
    }
}
