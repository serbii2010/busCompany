package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.BaseAccountDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.AdminMapper;
import net.thumbtack.school.buscompany.mappers.dto.ClientMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);
    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BaseAccountDtoResponse login(@Valid @RequestBody LoginDtoRequest loginDto, HttpServletResponse response)
                throws ServerException {
        Account account = accountService.getAccountByLogin(loginDto.getLogin());
        accountService.checkPassword(account, loginDto.getPassword());
        accountService.login(account, response);

        if (account.getUserType() == accountService.getUserTypeId(UserTypeEnum.ADMIN)) {
            LOGGER.debug("admin log in");
            return AdminMapper.INSTANCE.accountToDto(account);
        } else {
            LOGGER.debug("client log in");
            return ClientMapper.INSTANCE.accountToDto(account);
        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmptyDtoResponse logout(@CookieValue("JAVASESSIONID") String javaSessionId) {
        accountService.logout(javaSessionId);
        return new EmptyDtoResponse();
    }

}
