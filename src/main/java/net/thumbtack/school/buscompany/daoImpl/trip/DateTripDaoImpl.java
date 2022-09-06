package net.thumbtack.school.buscompany.daoImpl.trip;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateTripDaoImpl  extends DaoImplBase implements Dao<DateTrip> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTripDaoImpl.class);

    @Override
    public DateTrip findById(String id) throws ServerException {
        return null;
    }

    public DateTrip find(String tripId, String date) throws ServerException {
        LOGGER.debug("DAO get DateTrip");
        try (SqlSession sqlSession = getSession()) {
            return getDateTripMapper(sqlSession).find(tripId, date);
        } catch (RuntimeException ex) {
            LOGGER.warn("Can't get DateTrip {}", ex);
            throw new ServerException(ServerErrorCode.DATE_TRIP_NOT_FOUND);
        }
    }

    @Override
    public List<DateTrip> findAll() {
        return null;
    }

    @Override
    public DateTrip insert(DateTrip date) throws ServerException {
        LOGGER.debug("DAO insert DateTrip {}", date);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDateTripMapper(sqlSession).insert(date);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert DateTrip {} {}", date, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return date;
    }

    @Override
    public void remove(DateTrip dateTrip) throws ServerException {
        LOGGER.debug("DAO delete Trip {}", dateTrip);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDateTripMapper(sqlSession).delete(dateTrip);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete DateTrip {}", ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(DateTrip dateTrip) {

    }

    public void update(DateTrip dateTrip, int countPlace) throws ServerException {
        LOGGER.debug("DAO update Account {}", dateTrip);
        try (SqlSession sqlSession = getSession()) {
            try {
                if (getDateTripMapper(sqlSession).update(dateTrip, countPlace) == 0) {
                    throw new ServerException(ServerErrorCode.FREE_PLACE_NOT_FOUND);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update account {} {}", dateTrip, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
