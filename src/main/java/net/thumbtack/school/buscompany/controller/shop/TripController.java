package net.thumbtack.school.buscompany.controller.shop;

import net.thumbtack.school.buscompany.dto.request.shop.AddTripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.shop.AddTripDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddTripDtoResponse addTrip(@Valid @RequestBody AddTripDtoRequest request,
                                      @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        AddTripDtoResponse response = new AddTripDtoResponse();
        //@todo сохранить рейс и заполнить ответ
        return response;
    }
}
