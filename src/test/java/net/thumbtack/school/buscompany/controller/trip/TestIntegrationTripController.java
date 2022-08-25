package net.thumbtack.school.buscompany.controller.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.BusHelper;
import net.thumbtack.school.buscompany.helper.StationHelper;
import net.thumbtack.school.buscompany.helper.dto.request.trip.TripDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.trip.TripDtoResponseHelper;
import net.thumbtack.school.buscompany.service.DebugService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TestIntegrationTripController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DebugService debugService;
    @Autowired
    private BusHelper busHelper;
    @Autowired
    private StationHelper stationHelper;

    @Autowired
    private ObjectMapper mapper;

    private Cookie cookieAdmin;
    private Cookie cookieClient;

    @BeforeEach
    public void init() throws Exception {
        debugService.clear();
        busHelper.generateDefaultBuses();
        stationHelper.generateDefaultStation();
        cookieAdmin = new Cookie("JAVASESSIONID", AccountHelper.registrationAdmin("admin", mvc, mapper));
        cookieClient = new Cookie("JAVASESSIONID", AccountHelper.registrationClient(mvc, mapper));
    }

    @Test
    void addTrip_withDates() throws Exception {
        TripDtoRequest request = TripDtoRequestHelper.getWithDates();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithDates();

        mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void addTrip_withScheduleWeek() throws Exception {
        TripDtoRequest request = TripDtoRequestHelper.getWithScheduleWeek();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithScheduleWeek();

        mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void addTrip_withScheduleOdd() throws Exception {
        TripDtoRequest request = TripDtoRequestHelper.getWithScheduleOdd();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithScheduleOdd();

        mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void addTrip_withScheduleDaily() throws Exception {
        TripDtoRequest request = TripDtoRequestHelper.getWithScheduleDaily();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithScheduleDaily();

        mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void addTrip_withScheduleEven() throws Exception {
        TripDtoRequest request = TripDtoRequestHelper.getWithScheduleEven();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithScheduleEven();

        mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void addTrip_withScheduleMonth() throws Exception {
        TripDtoRequest request = TripDtoRequestHelper.getWithScheduleDayInMonth();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithScheduleDayInMonth();

        mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void update() {
    }

    @Test
    void approveTrip() {
    }

    @Test
    void deleteTrip() {
    }

    @Test
    void getTrip() {
    }

    @Test
    void getTrips() {
    }
}