package net.thumbtack.school.buscompany.daoImpl;

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
                getPlaceMapper(sqlSession).deleteAll();
                getPlaceMapper(sqlSession).resetAutoIncrement();
                getOrderMapper(sqlSession).deleteAll();
                getOrderMapper(sqlSession).resetAutoIncrement();
                getPassengerMapper(sqlSession).deleteAll();
                getPassengerMapper(sqlSession).resetAutoIncrement();
                getPassengerMapper(sqlSession).resetAutoIncrementFromOrderPassenger();
                getDateTripMapper(sqlSession).deleteAll();
                getDateTripMapper(sqlSession).resetAutoIncrement();
                getTripMapper(sqlSession).deleteAll();
                getTripMapper(sqlSession).resetAutoIncrement();
                getScheduleMapper(sqlSession).deleteAll();
                getScheduleMapper(sqlSession).resetAutoIncrement();
                getStationMapper(sqlSession).deleteAll();
                getStationMapper(sqlSession).resetAutoIncrement();
                getBusMapper(sqlSession).deleteAll();
                getBusMapper(sqlSession).resetAutoIncrement();
                getAccountMapper(sqlSession).deleteAll();
                getAccountMapper(sqlSession).resetAutoIncrement();
                getAdminMapper(sqlSession).resetAutoIncrement();
                getClientMapper(sqlSession).resetAutoIncrement();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
