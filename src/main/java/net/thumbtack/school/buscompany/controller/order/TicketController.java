package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.order.TicketMapper;
import net.thumbtack.school.buscompany.model.*;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.order.OrderService;
import net.thumbtack.school.buscompany.service.order.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/places")
public class TicketController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TicketService ticketService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> getFreePlaces(@PathVariable String id,
                                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.isClient(javaSessionId);
        Account account = accountService.getAuthAccount(javaSessionId);
        Order order = orderService.findById(id);
        orderService.checkAccount(order, account);
        return orderService.getFreePlaces(order);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TicketDtoResponse insertTicket(@Valid @RequestBody TicketDtoRequest request,
                                          @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkClient(javaSessionId);
        Passenger passenger = ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport());
        OrderPassenger orderPassenger = ticketService.getOrderPassenger(request.getOrderId(), passenger);
        Ticket ticket = new Ticket(orderPassenger, Integer.parseInt(request.getPlace()));

        ticketService.insert(ticket);
        return TicketMapper.INSTANCE.tickerToDto(ticket);
    }

}
