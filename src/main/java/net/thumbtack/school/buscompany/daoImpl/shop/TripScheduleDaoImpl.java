package net.thumbtack.school.buscompany.daoImpl.shop;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.TripSchedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripScheduleDaoImpl extends DaoImplBase implements Dao<TripSchedule> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripScheduleDaoImpl.class);

    @Override
    public TripSchedule findById(String id) throws ServerException {
        return null;
    }

    @Override
    public List<TripSchedule> findAll() {
        return null;
    }

    @Override
    public TripSchedule insert(TripSchedule tripSchedule) {
        LOGGER.debug("DAO insert TripSchedule {}", tripSchedule);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTripScheduleMapper(sqlSession).insert(tripSchedule);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert TripSchedule {} {}", tripSchedule, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return tripSchedule;
    }

    @Override
    public void remove(TripSchedule object) {

    }

    @Override
    public void update(TripSchedule object) {

    }
}
