package net.thumbtack.school.buscompany.daoImpl.trip;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Place;
import net.thumbtack.school.buscompany.model.Trip;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class TripDaoImpl extends DaoImplBase implements Dao<Trip> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripDaoImpl.class);

    @Override
    public Trip findById(String id) throws ServerException {
        LOGGER.debug("DAO get Trip by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getTripMapper(sqlSession).findById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Trip by Id {} {}", id, ex);
            throw new ServerException(ServerErrorCode.DATABASE_ERROR);
        }
    }

    public List<Trip> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, Boolean approved) throws ServerException {
        LOGGER.debug("DAO get Trip list from filter");
        try (SqlSession sqlSession = getSession()) {
            return getTripMapper(sqlSession).filterTrip(fromStation, toStation, busName, fromDate, toDate, approved);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Trip list from filter");
            throw new ServerException(ServerErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Trip> findAll() {
        return null;
    }

    @Override
    public Trip insert(Trip trip) throws ServerException {
        LOGGER.debug("DAO insert Trip {}", trip);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTripMapper(sqlSession).insert(trip);
                insertDateTrip(sqlSession, trip);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Trip {} {}", trip, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return trip;
    }

    @Override
    public void remove(Trip trip) throws ServerException {
        LOGGER.debug("DAO delete Trip {}", trip);
        try (SqlSession sqlSession = getSession()) {
            try {
                String id = String.valueOf(trip.getId());
                getDateTripMapper(sqlSession).deleteByTripId(id);
                getTripMapper(sqlSession).delete(trip);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Trip {}", ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(Trip trip) throws ServerException {
        LOGGER.debug("DAO update Account {}", trip);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTripMapper(sqlSession).update(trip);

                getScheduleMapper(sqlSession).update(trip.getSchedule());
                getDateTripMapper(sqlSession).deleteByTripId(String.valueOf(trip.getId()));
                insertDateTrip(sqlSession, trip);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update account {} {}", trip, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    private void insertDateTrip(SqlSession sqlSession, Trip trip) {
        for (DateTrip date: trip.getDates()) {
            DateTrip dateTrip = new DateTrip(trip, date.getDate());
            getDateTripMapper(sqlSession).insert(dateTrip);
            for (int numberPlace: IntStream.range(1, trip.getBus().getPlaceCount()+1).boxed().collect(Collectors.toList())) {
                getPlaceMapper(sqlSession).insertFree(new Place(numberPlace, dateTrip));
            }
        }
    }
}
