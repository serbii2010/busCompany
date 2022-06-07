package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.OrderDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDaoImpl orderDao;

    public Order findById(String id) throws ServerException {
        return orderDao.findById(id);
    }

    public void insert(Order order) {
        orderDao.insert(order);
    }

    public List<Order> getListOrder(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId) {
        return orderDao.filter(fromStation, toStation, busName, fromDate, toDate, clientId);
    }
}
