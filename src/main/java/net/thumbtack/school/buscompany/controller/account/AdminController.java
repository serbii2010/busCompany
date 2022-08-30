package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
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

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RegistrationAdminDtoResponse insertAdmin(@Valid @RequestBody RegistrationAdminDtoRequest adminDtoRequest, HttpServletResponse response)
            throws ServerException{
        return accountService.registrationAdmin(adminDtoRequest, response);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EditAdministratorDtoResponse updateAdmin(@Valid @RequestBody EditAdministratorDtoRequest request,
                                                     @CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        return accountService.updateAdmin(request, javaSessionId);
    }

}
