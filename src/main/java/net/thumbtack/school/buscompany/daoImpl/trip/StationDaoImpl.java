package net.thumbtack.school.buscompany.daoImpl.trip;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Station;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StationDaoImpl extends DaoImplBase implements Dao<Station> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationDaoImpl.class);

    @Override
    public Station findById(String id) throws ServerException {
        return null;
    }

    public Station findByName(String name) throws ServerException {
        LOGGER.debug("DAO get Station");
        try (SqlSession sqlSession = getSession()) {
            return getStationMapper(sqlSession).findByName(name);
        } catch (RuntimeException ex) {
            LOGGER.warn("Can't get Station {}", ex);
            throw new ServerException(ServerErrorCode.STATION_NOT_FOUND);
        }
    }

    @Override
    public List<Station> findAll() {
        return null;
    }

    @Override
    public Station insert(Station station) throws ServerException {
        LOGGER.debug("DAO insert Station {}", station);
        try (SqlSession sqlSession = getSession()) {
            try {
                getStationMapper(sqlSession).insert(station);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Station {} {}", station, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return station;
    }

    @Override
    public void remove(Station object) {

    }

    @Override
    public void update(Station object) {

    }
}
