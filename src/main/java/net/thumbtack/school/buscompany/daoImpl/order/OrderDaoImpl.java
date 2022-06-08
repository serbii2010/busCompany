package net.thumbtack.school.buscompany.daoImpl.order;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.OrderPassenger;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Ticket;
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
            throw ex;
        }
    }

    public List<Integer> getPlaces(Order order) {
        LOGGER.debug("DAO get Order by Id {}", order);
        try (SqlSession sqlSession = getSession()) {
            return getOrderMapper(sqlSession).findPlaces(order);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Order by Id {} {}", order, ex);
            throw ex;
        }
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order insert(Order order) {
        LOGGER.debug("DAO insert Order {}", order);
        try (SqlSession sqlSession = getSession()) {
            try {
                getOrderMapper(sqlSession).insert(order);
                for (Passenger passenger: order.getPassengers()) {
                    Passenger newPassenger = getPassengerMapper(sqlSession).find(passenger);
                    if (newPassenger == null) {
                        getPassengerMapper(sqlSession).insert(passenger);
                        newPassenger = passenger;
                    }
                    getOrderPassengerMapper(sqlSession).insert(new OrderPassenger(order, newPassenger));
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Order {} {}", order, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return order;
    }

    @Override
    public void remove(Order order) {
        LOGGER.debug("DAO delete Order {}", order);
        try (SqlSession sqlSession = getSession()) {
            try {
                List<OrderPassenger> listOrderPassenger = getOrderPassengerMapper(sqlSession).findByOrderId(String.valueOf(order.getId()));
                for (OrderPassenger orderPassenger: listOrderPassenger) {
                    Ticket ticket = getTicketMapper(sqlSession).findByOrderPassengerId(String.valueOf(orderPassenger.getId()));
                    getTicketMapper(sqlSession).delete(ticket);
                    getOrderPassengerMapper(sqlSession).delete(orderPassenger);
                }
                getOrderMapper(sqlSession).delete(order);

            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Order {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(Order object) {

    }

    public List<Order> filter(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId) {
        LOGGER.debug("DAO get Order list from filter");
        try (SqlSession sqlSession = getSession()) {
            return getOrderMapper(sqlSession).filter(fromStation, toStation, busName, fromDate, toDate, clientId);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Order list from filter");
            throw ex;
        }
    }
}
