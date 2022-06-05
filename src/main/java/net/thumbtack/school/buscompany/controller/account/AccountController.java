package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.response.account.BaseAccountInfoDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.account.AdminMapper;
import net.thumbtack.school.buscompany.mappers.dto.account.ClientMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseAccountInfoDtoResponse getInfo(@CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);

        if (account.getUserType().getType().equals(UserTypeEnum.CLIENT.getType())) {
            return ClientMapper.INSTANCE.accountToDtoInfo(account);
        } else if (account.getUserType().getType().equals(UserTypeEnum.ADMIN.getType())) {
            return AdminMapper.INSTANCE.accountToDtoInfo(account);
        } else {
            throw new ServerException(ServerErrorCode.USER_NOT_AUTHORIZATION);
        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmptyDtoResponse deleteAccount(@CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.logout(javaSessionId);
        accountService.deleteAccount(account);
        return new EmptyDtoResponse();
    }
}
