package net.thumbtack.school.buscompany.controller.shop;

import net.thumbtack.school.buscompany.dto.request.shop.CreateTripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.shop.CreateTripDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.shop.TripMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.*;
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
    @Autowired
    private StationService stationService;
    @Autowired
    private TripService tripService;
    @Autowired
    private BusService busService;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateTripDtoResponse addTrip(@Valid @RequestBody CreateTripDtoRequest request,
                                         @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Trip trip = TripMapper.INSTANCE.createTripDtoToTrip(request, stationService, busService, scheduleService);
        tripService.insert(trip);

        return TripMapper.INSTANCE.tripToDtoCreate(trip);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse deleteTrip(@PathVariable String id,
                                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Trip trip = tripService.findById(id);
        tripService.delete(trip);

        return new EmptyDtoResponse();
    }
}
