package net.thumbtack.school.buscompany.daoImpl.shop;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
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

    @Override
    public List<DateTrip> findAll() {
        return null;
    }

    @Override
    public DateTrip insert(DateTrip date) {
        LOGGER.debug("DAO insert DateTrip {}", date);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDateTripMapper(sqlSession).insert(date);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert DateTrip {} {}", date, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return date;
    }

    @Override
    public void remove(DateTrip object) {

    }

    @Override
    public void update(DateTrip object) {

    }
}
