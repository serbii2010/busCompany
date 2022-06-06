package net.thumbtack.school.buscompany.daoImpl.order;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.OrderPassenger;
import net.thumbtack.school.buscompany.model.Passenger;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDaoImpl extends DaoImplBase implements Dao<Order> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public Order findById(String id) throws ServerException {
        return null;
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
    public void remove(Order object) {

    }

    @Override
    public void update(Order object) {

    }
}