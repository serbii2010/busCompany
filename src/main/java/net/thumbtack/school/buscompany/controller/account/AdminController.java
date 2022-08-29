package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;
import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.account.AdminMapper;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
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
    public RegistrationAdminDtoResponse insertAdmin(@Valid @RequestBody RegistrationAdminDtoRequest adminDtoRequest, HttpServletResponse response) {
        Admin admin = AdminMapper.INSTANCE.registrationAdminDtoToAccount(adminDtoRequest);
        accountService.registrationAdmin(admin);
        LOGGER.debug("administrator registered");
        accountService.login(admin, response);
        return AdminMapper.INSTANCE.accountToDto(admin);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EditAdministratorDtoResponse updateAdmin(@Valid @RequestBody EditAdministratorDtoRequest request,
                                                     @CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        if (account.getUserType() != UserTypeEnum.ADMIN) {
            throw new ServerException(ServerErrorCode.ACTION_FORBIDDEN);
        }
        Admin admin = accountService.findAdmin(account);

        AdminMapper.INSTANCE.update(admin, request, accountService);
        accountService.updateAccount(admin);
        LOGGER.debug("admin updated");
        return AdminMapper.INSTANCE.accountEditToDto(admin);
    }

}
