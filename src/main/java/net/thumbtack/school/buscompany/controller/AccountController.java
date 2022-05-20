package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.dto.response.account.BaseAccountInfoDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.AdminMapper;
import net.thumbtack.school.buscompany.mappers.dto.ClientMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseAccountInfoDtoResponse getInfo(@CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);

        if (account.getUserType() == accountService.getUserTypeId(UserTypeEnum.CLIENT)) {
            return ClientMapper.INSTANCE.accountToDtoInfo(account);
        } else if (account.getUserType() == accountService.getUserTypeId(UserTypeEnum.ADMIN)) {
            return AdminMapper.INSTANCE.accountToDtoInfo(account);
        } else {
            throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
    }

    @DeleteMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmptyDtoResponse deleteAccount(@CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.logout(javaSessionId);
        accountService.deleteAccount(account);
        return new EmptyDtoResponse();
    }
}
