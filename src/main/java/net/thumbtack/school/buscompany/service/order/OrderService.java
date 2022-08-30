package net.thumbtack.school.buscompany.service.order;

import net.thumbtack.school.buscompany.daoImpl.order.OrderDaoImpl;
import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.order.OrderMapper;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TripService tripService;
    @Autowired
    private OrderDaoImpl orderDao;

    public OrderDtoResponse createOrder(OrderDtoRequest request, String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        Client client = accountService.findClient(account);

        Order order = OrderMapper.INSTANCE.dtoToOrder(request, tripService, client);
        checkApproved(order);
        orderDao.insert(order);

        return OrderMapper.INSTANCE.orderToDto(order);
    }
    public List<OrderDtoResponse> filter(String javaSessionId, String fromStation, String toStation, String busName, String fromDate, String toDate, String clientId)  throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        List<Order> orderList = new ArrayList<>();
        if (accountService.isAdmin(javaSessionId)) {
            orderList.addAll(getListOrder(fromStation, toStation, busName, fromDate, toDate, clientId));
        } else {
            Client client = accountService.findClient(account);
            orderList.addAll(getListOrder(fromStation, toStation, busName, fromDate, toDate, String.valueOf(client.getId())));
        }
        return OrderMapper.INSTANCE.orderListToDtoResponse(orderList);
    }

    public EmptyDtoResponse delete(String orderId) throws ServerException {
        Order order = findById(orderId);
        orderDao.remove(order);

        return new EmptyDtoResponse();
    }

    public Order findById(String id) throws ServerException {
        Order result = orderDao.findById(id);
        if (result == null) {
            throw new ServerException(ServerErrorCode.ORDER_NOT_FOUND);
        }
        return result;
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
}
