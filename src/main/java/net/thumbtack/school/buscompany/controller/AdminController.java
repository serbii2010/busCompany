package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.mappers.dto.AdminMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RegistrationAdminDtoResponse insertUser(@Valid @RequestBody RegistrationAdminDtoRequest adminDtoRequest) {
        Account admin = AdminMapper.INSTANCE.registrationAdminDtoToAccount(adminDtoRequest);
        accountService.registrationAdmin(admin);
        LOGGER.debug("administrator registered");
        return AdminMapper.INSTANCE.accountToDto(admin);
    }
}
