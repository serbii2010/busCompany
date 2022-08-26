package net.thumbtack.school.buscompany.controller.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.account.EmptyDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripClientDtoResponse;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.BusHelper;
import net.thumbtack.school.buscompany.helper.StationHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.helper.dto.request.trip.TripDtoRequestHelper;
import net.thumbtack.school.buscompany.helper.dto.response.EmptyResponseHelper;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void update_scheduleWeekToScheduleEven() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        TripDtoRequest request = TripDtoRequestHelper.getUpdateToScheduleEven();
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoUpdateWithEven();

        mvc.perform(put("/api/trips/" + tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void approveTrip() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoUpdateApproveWithWeek();

        mvc.perform(put("/api/trips/" + tripId + "/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void deleteTrip() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        EmptyDtoResponse response = EmptyResponseHelper.get();

        mvc.perform(delete("/api/trips/" + tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));

    }

    @Test
    void getTrip() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response = TripDtoResponseHelper.getDtoInsertWithScheduleWeek();

        mvc.perform(get("/api/trips/" + tripId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allByAdmin() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeek();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEven();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allEmptyByClient() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripClientDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allByClient() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripHelper.approveTrip(tripId1, cookieAdmin, mvc);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripHelper.approveTrip(tripId2, cookieAdmin, mvc);
        TripClientDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeekByClient();
        response1.setTripId(tripId1);
        TripClientDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEvenByClient();
        response2.setTripId(tripId2);

        List<TripClientDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieClient))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_fromStation() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeek();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEven();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromStation", "Omsk"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_fromStationNotFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromStation", "Omsk1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_toStation() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeek();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEven();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("toStation", "Новосибирск"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_toStationNotFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("toStation", "Omsk"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_busName() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeek();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEven();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("busName", "Пазик"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_busNameNotFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("busName", "Ikarus"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_fromDate() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeekFilterDates();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEvenFilterDates();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromDate", "2023-01-08"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_fromDateFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromDate", "2024-01-08"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_toDate() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeekFilterToDates();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEvenFilterToDates();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("toDate", "2023-01-08"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_toDateFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("toDate", "2022-01-08"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_fromToDate() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeekFilterFromToDates();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEvenFilterFromToDates();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromDate", "2023-01-08")
                .param("toDate", "2023-01-20"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allFilter() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        int tripId1 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        int tripId2 = TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        TripAdminDtoResponse response1 = TripDtoResponseHelper.getDtoInsertWithScheduleWeekFilterFromToDates();
        response1.setTripId(tripId1);
        TripAdminDtoResponse response2 = TripDtoResponseHelper.getDtoInsertWithScheduleEvenFilterFromToDates();
        response2.setTripId(tripId2);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>(Arrays.asList(response1, response2));

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-08")
                .param("toDate", "2023-01-20"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allFilterFromStationNotFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromStation", "Новосибирск")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-08")
                .param("toDate", "2023-01-20"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allFilterToStationNotFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск1")
                .param("busName", "Пазик")
                .param("fromDate", "2023-01-08")
                .param("toDate", "2023-01-20"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }

    @Test
    void getTrips_allFilterBusNotFound() throws Exception {
        TripDtoRequest requestInsert = TripDtoRequestHelper.getWithScheduleWeek();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);
        requestInsert = TripDtoRequestHelper.getWithScheduleEven();
        TripHelper.insertTrip(requestInsert, cookieAdmin, mvc, mapper);

        List<TripAdminDtoResponse> listResponse = new ArrayList<>();

        mvc.perform(get("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookieAdmin)
                .param("fromStation", "Omsk")
                .param("toStation", "Новосибирск")
                .param("busName", "Пазик1")
                .param("fromDate", "2023-01-08")
                .param("toDate", "2023-01-20"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listResponse)))
                .andExpect(cookie().doesNotExist("JAVASESSIONID"));
    }
}