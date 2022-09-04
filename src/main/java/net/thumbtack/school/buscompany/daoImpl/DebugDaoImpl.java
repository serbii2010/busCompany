package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DebugDaoImpl extends DaoImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugDaoImpl.class);

    public void clear() throws ServerException {
        LOGGER.debug("DAO clear database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getPassengerMapper(sqlSession).deleteAll();
                getTripMapper(sqlSession).deleteAll();
                getStationMapper(sqlSession).deleteAll();
                getBusMapper(sqlSession).deleteAll();
                getAccountMapper(sqlSession).deleteAll();

                getScheduleMapper(sqlSession).resetAutoIncrement();
                getStationMapper(sqlSession).resetAutoIncrement();
                getBusMapper(sqlSession).resetAutoIncrement();
                getAccountMapper(sqlSession).resetAutoIncrement();
                getAdminMapper(sqlSession).resetAutoIncrement();
                getClientMapper(sqlSession).resetAutoIncrement();
                getPlaceMapper(sqlSession).resetAutoIncrement();
                getOrderMapper(sqlSession).resetAutoIncrement();
                getTripMapper(sqlSession).resetAutoIncrement();
                getPassengerMapper(sqlSession).resetAutoIncrement();
                getPassengerMapper(sqlSession).resetAutoIncrementFromOrderPassenger();
                getDateTripMapper(sqlSession).resetAutoIncrement();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
