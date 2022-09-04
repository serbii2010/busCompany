package net.thumbtack.school.buscompany.daoImpl.trip;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Bus;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusDaoImpl extends DaoImplBase implements Dao<Bus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusDaoImpl.class);

    @Override
    public Bus findById(String id) throws ServerException {
        return null;
    }

    public Bus findByName(String name) throws ServerException {
        LOGGER.debug("DAO get Bus");
        try (SqlSession sqlSession = getSession()) {
            return getBusMapper(sqlSession).findByName(name);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Bus {}", ex);
            throw new ServerException(ServerErrorCode.BUS_NOT_FOUND);
        }
    }

    @Override
    public List<Bus> findAll() throws ServerException {
        LOGGER.debug("DAO get Buses");
        try (SqlSession sqlSession = getSession()) {
            return getBusMapper(sqlSession).findAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Buses {}", ex);
            throw new ServerException(ServerErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Bus insert(Bus bus) throws ServerException {
        LOGGER.debug("DAO insert Bus {}", bus);
        try (SqlSession sqlSession = getSession()) {
            try {
                getBusMapper(sqlSession).insert(bus);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Bus {} {}", bus, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return bus;
    }

    @Override
    public void remove(Bus object) {

    }

    @Override
    public void update(Bus object) {

    }
}
