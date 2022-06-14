package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.account.ClientMapper;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RegistrationClientDtoResponse insertClient(@Valid @RequestBody RegistrationClientDtoRequest clientDtoRequest, HttpServletResponse response) {
        Client client = ClientMapper.INSTANCE.registrationDtoToAccount(clientDtoRequest);
        accountService.registrationClient(client);
        LOGGER.debug("client registered");
        accountService.login(client, response);
        return ClientMapper.INSTANCE.accountToDto(client);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EditClientDtoResponse updateClient(@Valid @RequestBody EditClientDtoRequest request,
                                              @CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        if (account.getUserType() != UserTypeEnum.CLIENT) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
        Client client = accountService.findClient(accountService.getAuthAccount(javaSessionId));
        ClientMapper.INSTANCE.update(client, request, accountService);
        accountService.updateAccount(account);
        LOGGER.debug("client updated");
        return ClientMapper.INSTANCE.accountEditToDto(client);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<InfoClientDtoResponse> getClients(@CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);

        List<InfoClientDtoResponse> result = new ArrayList<>();

        List<Client> accountsClient = accountService.getClients();
        for (Account acc: accountsClient) {
            result.add(ClientMapper.INSTANCE.accountToDtoInfo(accountService.findClient(acc)));
        }
        return result;
    }
}
