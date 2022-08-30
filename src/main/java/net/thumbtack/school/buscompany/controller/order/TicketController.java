package net.thumbtack.school.buscompany.controller.order;

import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
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
    private TicketService ticketService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> getFreePlaces(@PathVariable String id,
                                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkClient(javaSessionId);
        return ticketService.getFreePlace(javaSessionId, id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TicketDtoResponse insertTicket(@Valid @RequestBody TicketDtoRequest request,
                                          @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkClient(javaSessionId);
        return ticketService.insertTicket(javaSessionId, request);
    }

}
