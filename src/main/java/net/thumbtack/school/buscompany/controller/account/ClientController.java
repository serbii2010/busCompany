package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RegistrationClientDtoResponse insertClient(@Valid @RequestBody RegistrationClientDtoRequest clientDtoRequest, HttpServletResponse response)
            throws ServerException {
        return accountService.registrationClient(clientDtoRequest, response);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EditClientDtoResponse updateClient(@Valid @RequestBody EditClientDtoRequest request,
                                              @CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        return accountService.updateClient(request, javaSessionId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<InfoClientDtoResponse> getClients(@CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);
        return accountService.getClients();
    }
}
