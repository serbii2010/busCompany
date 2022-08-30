package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.response.account.BaseAccountInfoDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
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
        return accountService.getInfo(javaSessionId);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmptyDtoResponse deleteAccount(@CookieValue("JAVASESSIONID") String javaSessionId)
            throws ServerException {
        return accountService.deleteAccount(javaSessionId);
    }
}
