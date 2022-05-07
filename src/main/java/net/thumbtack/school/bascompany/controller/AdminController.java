package net.thumbtack.school.bascompany.controller;

import net.thumbtack.school.bascompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.bascompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.bascompany.mapper.AdminMapper;
import net.thumbtack.school.bascompany.model.Account;
import net.thumbtack.school.bascompany.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationAdminDtoResponse insertUser(@Valid @RequestBody RegistrationAdminDtoRequest adminDtoRequest) {
        Account admin = AdminMapper.INSTANCE.registrationAdminDtoToAccount(adminDtoRequest);
        userService.registrationAdmin(admin);
        LOGGER.debug("user registered");
        return AdminMapper.INSTANCE.accountToDto(admin);
    }
}
