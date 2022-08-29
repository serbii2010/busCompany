package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.order.TicketMapper;
import net.thumbtack.school.buscompany.model.Order;
import net.thumbtack.school.buscompany.model.Passenger;
import net.thumbtack.school.buscompany.model.Place;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
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
        accountService.checkClient(javaSessionId);
        Account account = accountService.getAuthAccount(javaSessionId);
        Client client = accountService.findClient(account);
        Order order = orderService.findById(id);
        orderService.checkAccount(order, client);
        return orderService.getFreePlaces(order);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TicketDtoResponse insertTicket(@Valid @RequestBody TicketDtoRequest request,
                                          @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkClient(javaSessionId);
        Passenger passenger = ticketService.getPassenger(request.getFirstName(), request.getLastName(), request.getPassport());
        Order order = orderService.findById(request.getOrderId());
        Place ticket = ticketService.findPlace(Integer.parseInt(request.getPlace()), order.getDateTrip());
        ticket.setPassenger(passenger);

        ticketService.choose(ticket);
        return TicketMapper.INSTANCE.tickerToDto(ticket, order, passenger);
    }

}
