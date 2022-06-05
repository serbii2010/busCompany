package net.thumbtack.school.buscompany.controller.trip;

import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.trip.TripMapper;
import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.trip.BusService;
import net.thumbtack.school.buscompany.service.trip.ScheduleService;
import net.thumbtack.school.buscompany.service.trip.StationService;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public TripDtoResponse addTrip(@Valid @RequestBody TripDtoRequest request,
                                   @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Trip trip = TripMapper.INSTANCE.tripDtoToTrip(request, stationService, busService, scheduleService);
        tripService.insert(trip);

        return TripMapper.INSTANCE.tripToDtoResponse(trip);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripDtoResponse update(@Valid @RequestBody TripDtoRequest tripDtoRequest,
            @PathVariable String id,
            @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Trip trip = tripService.findById(id);
        TripMapper.INSTANCE.update(trip, tripDtoRequest, stationService, busService, scheduleService, tripService);

        tripService.update(trip);
        return TripMapper.INSTANCE.tripToDtoResponse(trip);
    }

    @PutMapping(path = "/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripDtoResponse approveTrip(@PathVariable String id,
                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Trip trip = tripService.findById(id);
        trip.setApproved(true);
        tripService.update(trip);
        return TripMapper.INSTANCE.tripToDtoResponse(trip);
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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripDtoResponse getTrip(@PathVariable String id,
                                   @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        Account account = accountService.getAuthAccount(javaSessionId);
        accountService.checkIfAdmin(account);

        Trip trip = tripService.findById(id);

        return TripMapper.INSTANCE.tripToDtoResponse(trip);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDtoResponse> getTrips(@CookieValue("JAVASESSIONID") String javaSessionId,
                                          @RequestParam(value = "fromStation", required = false) String fromStation,
                                          @RequestParam(value = "toStation", required = false) String toStation,
                                          @RequestParam(value = "busName", required = false) String busName,
                                          @RequestParam(value = "fromDate", required = false) String fromDate,
                                          @RequestParam(value = "toDate", required = false) String toDate
                                          ) throws ServerException {
        accountService.getAuthAccount(javaSessionId);
        List<Trip> listTrip = tripService.getListTrip(fromStation, toStation, busName, fromDate, toDate);
        return TripMapper.INSTANCE.tripListToDtoResponse(listTrip);
    }

}
