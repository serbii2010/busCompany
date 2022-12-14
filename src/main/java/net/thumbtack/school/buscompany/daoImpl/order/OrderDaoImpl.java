package net.thumbtack.school.buscompany.daoImpl.order;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl extends DaoImplBase implements Dao<Order> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public Order findById(String id) throws ServerException {
        LOGGER.debug("DAO get Order by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getOrderMapper(sqlSession).findById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Order by Id {} {}", id, ex);
            throw new ServerException(ServerErrorCode.DATABASE_ERROR);
        }
    }

    public List<Integer> getFreePlaces(Order order) throws ServerException {
        LOGGER.debug("DAO get Order by Id {}", order);
        try (SqlSession sqlSession = getSession()) {
            return getOrderMapper(sqlSession).findFreePlaces(order);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Order by Id {} {}", order, ex);
            throw new ServerException(ServerErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order insert(Order order) throws ServerException {
        LOGGER.debug("DAO insert Order {}", order);
        try (SqlSession sqlSession = getSession()) {
            try {
                if (getOrderMapper(sqlSession).insert(order) == 0) {
                    throw new ServerException(ServerErrorCode.FREE_PLACE_NOT_FOUND);
                }
                for (Passenger passenger : order.getPassengers()) {
                    Passenger newPassenger = getPassengerMapper(sqlSession).find(passenger);
                    if (newPassenger == null) {
                        getPassengerMapper(sqlSession).insert(passenger);
                        newPassenger = passenger;
                    }
                    getPassengerMapper(sqlSession).insertOrderPassenger(order, newPassenger);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Order {} {}", order, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
        return order;
    }

    @Override
    public void remove(Order order) throws ServerException {
        LOGGER.debug("DAO delete Order {}", order);
        try (SqlSession sqlSession = getSession()) {
            try {
                getOrderMapper(sqlSession).delete(order);
                getPlaceMapper(sqlSession).setFreePlaceByOrder(order);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Order {}", ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.DATABASE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(Order object) {

    }

    public List<Order> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId)
            throws ServerException {
        LOGGER.debug("DAO get Order list from filter");
        try (SqlSession sqlSession = getSession()) {
            return getOrderMapper(sqlSession).filter(fromStation, toStation, busName, fromDate, toDate, clientId);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Order list from filter");
            throw new ServerException(ServerErrorCode.DATABASE_ERROR);
        }
    }
}
