package net.thumbtack.school.buscompany.daoImpl.trip;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.DateTrip;
import net.thumbtack.school.buscompany.model.Place;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceDaoImpl extends DaoImplBase implements Dao<Place> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceDaoImpl.class);
    @Override
    public Place findById(String id) throws ServerException {
        return null;
    }

    public Place find(int number, DateTrip dateTrip) throws ServerException {
        LOGGER.debug("DAO find Place ");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getPlaceMapper(sqlSession).find(String.valueOf(number), dateTrip);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't find Place {}", ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public List<Place> findAll() {
        return null;
    }

    @Override
    public Place insert(Place place) throws ServerException {
        LOGGER.debug("DAO insert Place {}", place);
        try (SqlSession sqlSession = getSession()) {
            try {
                getPlaceMapper(sqlSession).insert(place);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Place {} {}", place, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return place;
    }

    public Place insertFree(Place place) throws ServerException {
        LOGGER.debug("DAO insert Place {}", place);
        try (SqlSession sqlSession = getSession()) {
            try {
                getPlaceMapper(sqlSession).insertFree(place);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Place {} {}", place, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return place;
    }

    @Override
    public void remove(Place object) {

    }

    @Override
    public void update(Place place) throws ServerException {
        LOGGER.debug("DAO update Place {}", place);
        try (SqlSession sqlSession = getSession()) {
            try {
                getPlaceMapper(sqlSession).setFreePlace(place);
                getPlaceMapper(sqlSession).update(place);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update Place {} {}", place, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }


}
