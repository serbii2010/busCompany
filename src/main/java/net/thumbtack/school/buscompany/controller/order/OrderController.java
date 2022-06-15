package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.order.OrderMapper;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TripService tripService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDtoResponse createOrder(@Valid @RequestBody OrderDtoRequest request,
                                        @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkClient(javaSessionId);
        Client client = accountService.findClient(account);

        Order order = OrderMapper.INSTANCE.dtoToOrder(request, tripService, client);
        orderService.insert(order);

        return OrderMapper.INSTANCE.orderToDto(order);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDtoResponse> filter(
            @CookieValue("JAVASESSIONID") String javaSessionId,
            @RequestParam(value = "fromStation", required = false) String fromStation,
            @RequestParam(value = "toStation", required = false) String toStation,
            @RequestParam(value = "busName", required = false) String busName,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "clintId", required = false) String clientId
    ) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        List<Order> orderList = new ArrayList<>();
        if (accountService.isAdmin(javaSessionId)) {
            orderList.addAll(orderService.getListOrder(fromStation, toStation, busName, fromDate, toDate, clientId));
        } else {
            orderList.addAll(orderService.getListOrder(fromStation, toStation, busName, fromDate, toDate, String.valueOf(account.getId())));
        }
        return OrderMapper.INSTANCE.orderListToDtoResponse(orderList);
    }


    @DeleteMapping(path = "/{orderId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse deleteTicket(@PathVariable String orderId,
                                         @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkClient(javaSessionId);

        Order order = orderService.findById(orderId);
        orderService.delete(order);

        return new EmptyDtoResponse();
    }
}
