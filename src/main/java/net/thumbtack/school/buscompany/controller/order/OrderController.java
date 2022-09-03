package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDtoResponse createOrder(@Valid @RequestBody OrderDtoRequest request,
                                        @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        return orderService.createOrder(request, javaSessionId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDtoResponse> filter(
            @CookieValue("JAVASESSIONID") String javaSessionId,
            @RequestParam(value = "fromStation", required = false) String fromStation,
            @RequestParam(value = "toStation", required = false) String toStation,
            @RequestParam(value = "busName", required = false) String busName,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "clientId", required = false) String clientId
    ) throws ServerException {
        return orderService.filter(javaSessionId, fromStation, toStation, busName, fromDate, toDate, clientId);
    }

    @DeleteMapping(path = "/{orderId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse deleteOrder(@PathVariable String orderId,
                                         @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        return orderService.delete(javaSessionId, orderId);
    }
}
