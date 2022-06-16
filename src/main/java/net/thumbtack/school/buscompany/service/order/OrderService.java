package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.OrderDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.account.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDaoImpl orderDao;

    public Order findById(String id) throws ServerException {
        Order result = orderDao.findById(id);
        if (result == null) {
            throw new ServerException(ServerErrorCode.ORDER_NOT_FOUND);
        }
        return result;
    }

    public void insert(Order order) {
        orderDao.insert(order);
    }

    public void checkApproved(Order order) throws ServerException{
        if (!order.getDateTrip().getTrip().isApproved()) {
            throw new ServerException(ServerErrorCode.TRIP_NOT_FOUND);
        }
    }

    public List<Order> getListOrder(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId) {
        return orderDao.filter(fromStation, toStation, busName, fromDate, toDate, clientId);
    }

    public List<Integer> getFreePlaces(Order order) {
        return orderDao.getFreePlaces(order);
    }

    public void checkAccount(Order order, Client client) throws ServerException {
        if (order.getClient().getId() != client.getId()) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }

    public void delete(Order order) throws ServerException {
        orderDao.remove(order);
    }
}
