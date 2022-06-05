package net.thumbtack.school.buscompany.daoImpl.trip;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Schedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleDaoImpl extends DaoImplBase implements Dao<Schedule> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleDaoImpl.class);

    @Override
    public Schedule findById(String id) throws ServerException {
        return null;
    }

    public Schedule find(Schedule schedule) throws ServerException{
        LOGGER.debug("DAO get Schedule");
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).find(schedule);
        } catch (RuntimeException ex) {
            LOGGER.warn("Can't get Schedule {}", ex);
            throw new ServerException(ServerErrorCode.SCHEDULE_NOT_FOUND);
        }
    }

    @Override
    public List<Schedule> findAll() {
        return null;
    }

    @Override
    public Schedule insert(Schedule schedule) {
        LOGGER.debug("DAO insert Schedule {}", schedule);
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).insert(schedule);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Schedule {} {}", schedule, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return schedule;
    }

    @Override
    public void remove(Schedule object) {

    }

    @Override
    public void update(Schedule object) {

    }
}
