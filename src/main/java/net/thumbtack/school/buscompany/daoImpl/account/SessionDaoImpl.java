package net.thumbtack.school.buscompany.daoImpl.account;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Session;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionDaoImpl extends DaoImplBase implements Dao<Session> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDaoImpl.class);

    public Session findBySessionId(String sessionId) throws ServerException {
        LOGGER.debug("DAO find Account by sessionId {}", sessionId);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSessionMapper(sqlSession).getAccountBySessionId(sessionId);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get account {} {}", sessionId, ex);
                throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
            }
        }
    }

    public Session findByAccountId(int id) throws ServerException {
        LOGGER.debug("DAO find Account by accountId {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSessionMapper(sqlSession).getAccountByAccountId(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get account {} {}", id, ex);
                throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
            }
        }
    }

    @Override
    public Session findById(String id) throws ServerException {
        return null;
    }

    @Override
    public List<Session> findAll() {
        return null;
    }

    @Override
    public Session insert(Session session) {
        LOGGER.debug("DAO insert Session {}", session);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).insert(session);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert session {} {}", session, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return session;
    }

    @Override
    public void remove(Session session) {
        LOGGER.debug("DAO delete Session {}", session);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).delete(session);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete session {} {}", session, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(Session session) {
        LOGGER.debug("DAO update session {}", session);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).update(session);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update session {} {}", session, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
