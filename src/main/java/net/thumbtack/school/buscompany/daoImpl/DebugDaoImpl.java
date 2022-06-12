package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.daoImpl.trip.TripDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DebugDaoImpl extends DaoImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugDaoImpl.class);

    public void clear() {
        LOGGER.debug("DAO clear database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getTicketMapper(sqlSession).deleteAll();
                getOrderPassengerMapper(sqlSession).deleteAll();
                getOrderMapper(sqlSession).deleteAll();
                getPassengerMapper(sqlSession).deleteAll();
                getDateTripMapper(sqlSession).deleteAll();
                getTripMapper(sqlSession).deleteAll();
                getScheduleMapper(sqlSession).deleteAll();
                getStationMapper(sqlSession).deleteAll();
                getBusMapper(sqlSession).deleteAll();
                getAccountMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
