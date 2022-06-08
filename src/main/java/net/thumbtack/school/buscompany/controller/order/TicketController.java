package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/places")
public class TicketController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> getFreePlaces(@PathVariable String id,
                                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.isClient(javaSessionId);
        Account account = accountService.getAuthAccount(javaSessionId);
        Order order = orderService.findById(id);

        List<Integer> freePlace = orderService.getFreePlaces(order);
        //@todo получить список свободных мест
        return freePlace;
    }
}
