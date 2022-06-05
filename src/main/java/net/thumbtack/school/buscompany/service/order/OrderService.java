package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.OrderDaoImpl;
import net.thumbtack.school.buscompany.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderDaoImpl orderDao;

    public void insert(Order order) {
        orderDao.insert(order);
    }
}
