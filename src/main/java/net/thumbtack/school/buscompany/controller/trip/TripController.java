package net.thumbtack.school.buscompany.controller.trip;

import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.BaseTripDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.service.account.AccountService;
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
    private TripService tripService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse addTrip(@Valid @RequestBody TripDtoRequest request,
                                        @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);

        return tripService.addTrip(request);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse update(@Valid @RequestBody TripDtoRequest tripDtoRequest,
                                       @PathVariable String id,
                                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);
        return tripService.update(id, tripDtoRequest);
    }

    @PutMapping(path = "/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse approveTrip(@PathVariable String id,
                                            @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);

        return tripService.approved(id);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse deleteTrip(@PathVariable String id,
                                       @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);

        return tripService.delete(id);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TripAdminDtoResponse getTrip(@PathVariable String id,
                                        @CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);

        return tripService.getTrip(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends BaseTripDtoResponse> getTrips(@CookieValue("JAVASESSIONID") String javaSessionId,
                                              @RequestParam(value = "fromStation", required = false) String fromStation,
                                              @RequestParam(value = "toStation", required = false) String toStation,
                                              @RequestParam(value = "busName", required = false) String busName,
                                              @RequestParam(value = "fromDate", required = false) String fromDate,
                                              @RequestParam(value = "toDate", required = false) String toDate
                                          ) throws ServerException {
        return tripService.filter(javaSessionId, fromStation, toStation, busName, fromDate, toDate);
    }

}
