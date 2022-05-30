package net.thumbtack.school.buscompany.daoImpl.shop;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.model.TripSchedule;
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
        return null;
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
                if (trip.getSchedule() != null) {
                    TripSchedule tripSchedule = new TripSchedule(trip);
                    getTripScheduleMapper(sqlSession).insert(tripSchedule);
                    //@todo сохранить даты
                }
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
    public void remove(Trip object) {

    }

    @Override
    public void update(Trip object) {

    }
}
