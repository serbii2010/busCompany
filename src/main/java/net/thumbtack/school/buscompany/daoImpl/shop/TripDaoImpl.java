package net.thumbtack.school.buscompany.daoImpl.shop;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripDaoImpl extends DaoImplBase implements Dao<Trip> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripDaoImpl.class);

    @Override
    public Trip findById(String id) throws ServerException {
        LOGGER.debug("DAO get Trip by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getTripMapper(sqlSession).findById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Trip by Id {} {}", id, ex);
            throw ex;
        }
    }

    public List<Trip> filter(String fromStation, String toStation, String busName, String fromDate, String toDate) {
        LOGGER.debug("DAO get Trip list from filter");
        try (SqlSession sqlSession = getSession()) {
            return getTripMapper(sqlSession).filter(fromStation, toStation, busName, fromDate, toDate);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Trip list from filter");
            throw ex;
        }
    }

    @Override
    public List<Trip> findAll() {
        return null;
    }

    @Override
    public Trip insert(Trip trip) {
        LOGGER.debug("DAO insert Trip {}", trip);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTripMapper(sqlSession).insert(trip);
                //@todo сохранить даты
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Trip {} {}", trip, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return trip;
    }

    @Override
    public void remove(Trip trip) {
        LOGGER.debug("DAO delete Trip {}", trip);
        try (SqlSession sqlSession = getSession()) {
            try {
                String id = String.valueOf(trip.getId());
                getDateTripMapper(sqlSession).deleteByTripId(id);
                getTripMapper(sqlSession).delete(trip);

            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Trip {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(Trip trip) {
        LOGGER.debug("DAO update Account {}", trip);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTripMapper(sqlSession).update(trip);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update account {} {}", trip, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
