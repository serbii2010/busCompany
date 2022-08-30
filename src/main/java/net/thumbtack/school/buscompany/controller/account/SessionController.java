package net.thumbtack.school.buscompany.controller.account;

import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.BaseAccountDtoResponse;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
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
@RequestMapping("/api/session")
public class SessionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);
    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BaseAccountDtoResponse login(@Valid @RequestBody LoginDtoRequest loginDto,
                                        HttpServletResponse response)
            throws ServerException {
        return  accountService.login(loginDto, response);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmptyDtoResponse logout(@CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.logout(javaSessionId);
        return new EmptyDtoResponse();
    }

}
