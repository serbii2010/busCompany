package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.OrderDaoImpl;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public List<Order> getListOrder(String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId) {
        return orderDao.filter(fromStation, toStation, busName, fromDate, toDate, clientId);
    }

    public List<Integer> getFreePlaces(Order order) {
        List<Integer> place = orderDao.getPlaces(order);
        int countPlace = order.getTrip().getBus().getPlaceCount();
        return IntStream.range(1, countPlace+1).filter(p -> !place.contains(p)).boxed().collect(Collectors.toList());
    }

    public List<Integer> getPlaces(Order order) {
        return orderDao.getPlaces(order);
    }

    public void checkAccount(Order order, Account account) throws ServerException {
        if (order.getAccount().getId() != account.getId()) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
    }
}
