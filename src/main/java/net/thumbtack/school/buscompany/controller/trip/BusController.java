package net.thumbtack.school.buscompany.controller.trip;

import net.thumbtack.school.buscompany.dto.response.trip.BusDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.mappers.dto.trip.BusMapper;
import net.thumbtack.school.buscompany.model.Bus;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.trip.BusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusService busService;
    @Autowired
    private AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<BusDtoResponse> getClients(@CookieValue("JAVASESSIONID") String javaSessionId) throws ServerException {
        accountService.checkAdmin(javaSessionId);

        List<BusDtoResponse> responseList = new ArrayList<>();
        for (Bus bus: busService.getBuses()) {
            responseList.add(BusMapper.INSTANCE.busToDto(bus));
        }
        return responseList;
    }
}
