package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;
import net.thumbtack.school.buscompany.mappers.dto.ClientMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/clients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RegistrationClientDtoResponse insertClient(@Valid @RequestBody RegistrationClientDtoRequest clientDtoRequest) {
        Account client = ClientMapper.INSTANCE.registrationDtoToAccount(clientDtoRequest);
        accountService.registrationClient(client);
        LOGGER.debug("client registered");
        return ClientMapper.INSTANCE.accountToDto(client);
    }
}
