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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/admins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RegistrationAdminDtoResponse insertAdmin(@Valid @RequestBody RegistrationAdminDtoRequest adminDtoRequest, HttpServletResponse response) {
        Account admin = AdminMapper.INSTANCE.registrationAdminDtoToAccount(adminDtoRequest);
        accountService.registrationAdmin(admin);
        LOGGER.debug("administrator registered");
        login(admin, response);
        return AdminMapper.INSTANCE.accountToDto(admin);
    }

    private void login(Account admin, HttpServletResponse response) {
        if (admin.getId() == 0) {
            return;
        }
        UUID uuid = UUID.randomUUID();
        UUID result = accountService.getAdmins().putIfAbsent(admin, uuid);
        if (result == null) {
            result = uuid;
        }
        Cookie cookie = new Cookie("JAVASESSIONID", result.toString());
        response.addCookie(cookie);
    }
}
