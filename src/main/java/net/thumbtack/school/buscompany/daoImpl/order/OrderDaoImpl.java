package net.thumbtack.school.buscompany.daoImpl.order;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
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
        LOGGER.debug("DAO insert DateTrip {}", order);
        try (SqlSession sqlSession = getSession()) {
            try {
                getOrderMapper(sqlSession).insert(order);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert DateTrip {} {}", order, ex);
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
