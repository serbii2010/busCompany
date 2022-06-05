package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.order.OrderMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDtoResponse createOrder(@Valid @RequestBody OrderDtoRequest request,
                                        @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Order order = OrderMapper.INSTANCE.dtoToOrder(request);

        return OrderMapper.INSTANCE.orderToDto(order);
    }
}
