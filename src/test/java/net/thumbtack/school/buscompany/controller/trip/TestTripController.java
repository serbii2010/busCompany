package net.thumbtack.school.buscompany.controller.trip;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.request.trip.ScheduleDtoRequest;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import net.thumbtack.school.buscompany.helper.AccountHelper;
import net.thumbtack.school.buscompany.helper.DateTripHelper;
import net.thumbtack.school.buscompany.helper.TripHelper;
import net.thumbtack.school.buscompany.model.Schedule;
import net.thumbtack.school.buscompany.model.Trip;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.service.account.AccountService;
import net.thumbtack.school.buscompany.service.trip.BusService;
import net.thumbtack.school.buscompany.service.trip.ScheduleService;
import net.thumbtack.school.buscompany.service.trip.StationService;
import net.thumbtack.school.buscompany.service.trip.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.text.ParseException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {TripController.class, TripHelper.class, DateTripHelper.class, AccountHelper.class})
class TestTripController {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private AccountHelper accountHelper;

    @MockBean
    private AccountService accountService;
    @MockBean
    private StationService stationService;
    @MockBean
    private TripService tripService;
    @MockBean
    private BusService busService;
    @MockBean
    private ScheduleService scheduleService;

    private Cookie cookie;
    private Client client;
    private Trip trip;
    private Schedule schedule;

    @BeforeEach
    public void init() throws ParseException {
        accountHelper.init();
        cookie = accountHelper.getCookie();
        client = accountHelper.getClient();

        tripHelper.init();
        trip = tripHelper.getTrip();
    }

    @Test
    void testAddTrip_byDates() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_withoutDatesAndSchedules() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                null
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_withDatesAndSchedules() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                new ScheduleDtoRequest(),
                Collections.singletonList("2022-12-12")
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }


    @Test
    void testAddTrip_fromStationNotFound() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(stationService.findStationByName("Omsk")).thenThrow(new ServerException(ServerErrorCode.STATION_NOT_FOUND));

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_toStationNotFound() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(stationService.findStationByName("Новосибирск")).thenThrow(new ServerException(ServerErrorCode.STATION_NOT_FOUND));

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_badTimeStart() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "32:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_badDuration() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:61",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_badFormatDateInDates() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("202-12-12")
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_scheduleEmpty() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                new ScheduleDtoRequest(),
                null
        );

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_schedule() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                new ScheduleDtoRequest(
                        "2022-12-01",
                        "2022-12-25",
                        "odd"
                ),
                null
        );

        Mockito.when(scheduleService.checkPeriod("odd")).thenReturn(true);
        Mockito.when(tripService.insert(trip)).thenReturn(trip);
        Mockito.when(scheduleService.findOrInsert(schedule)).thenReturn(schedule);

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void testAddTrip_badBusName() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(busService.findByName("Пазик")).thenThrow(new ServerException(ServerErrorCode.BUS_NOT_FOUND));

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }


    @Test
    void testAddTrip_scheduleNotAdmin() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                new ScheduleDtoRequest(
                        "2022-12-01",
                        "2022-12-25",
                        "odd"
                ),
                null
        );

        Mockito.when(scheduleService.checkPeriod("odd")).thenReturn(true);
        Mockito.when(tripService.insert(trip)).thenReturn(trip);
        Mockito.when(scheduleService.findOrInsert(schedule)).thenReturn(schedule);
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(cookie.getValue());

        MvcResult result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }


    @Test
    void testUpdate() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdate_notAdmin() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(cookie.getValue());

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdate_badBusName() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.when(busService.findByName("Пазик")).thenThrow(new ServerException(ServerErrorCode.BUS_NOT_FOUND));

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdate_badFromStation() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.when(stationService.findStationByName("Omsk")).thenThrow(new ServerException(ServerErrorCode.STATION_NOT_FOUND));

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdate_badToStation() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.when(stationService.findStationByName("Новосибирск")).thenThrow(new ServerException(ServerErrorCode.STATION_NOT_FOUND));

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdate_badStartTimeFormat() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12-30",
                "23:51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testUpdate_badDurationTimeFormat() throws Exception {
        TripDtoRequest request = new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23*51",
                20,
                null,
                Collections.singletonList("2022-12-12")
        );

        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());

        MvcResult result = mvc.perform(put("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testApproveTrip() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());

        MvcResult result = mvc.perform(put("/api/trips/1/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void testApproveTrip_tripNotFound() throws Exception {
        Mockito.when(tripService.findById("1")).thenThrow(new ServerException(ServerErrorCode.TRIP_NOT_FOUND));

        MvcResult result = mvc.perform(put("/api/trips/1/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testApproveTrip_notAdmin() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(cookie.getValue());

        MvcResult result = mvc.perform(put("/api/trips/1/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testDeleteTrip() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());

        MvcResult result = mvc.perform(delete("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void testDeleteTrip_notAdmin() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(cookie.getValue());

        MvcResult result = mvc.perform(delete("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testDeleteTrip_notFoundTrip() throws Exception {
        Mockito.when(tripService.findById("1")).thenThrow(new ServerException(ServerErrorCode.TRIP_NOT_FOUND));

        MvcResult result = mvc.perform(delete("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testDeleteTrip_approvedTrip() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(tripService).checkNotApproved(tripHelper.getTrip());

        MvcResult result = mvc.perform(delete("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testGetTrip() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());

        MvcResult result = mvc.perform(get("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void testGetTrip_notAdmin() throws Exception {
        Mockito.when(tripService.findById("1")).thenReturn(tripHelper.getTrip());
        Mockito.doThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN)).when(accountService).checkAdmin(cookie.getValue());

        MvcResult result = mvc.perform(get("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void testGetTrip_notFoundTrip() throws Exception {
        Mockito.when(tripService.findById("1")).thenThrow(new ServerException(ServerErrorCode.ACTION_FORBIDDEN));

        MvcResult result = mvc.perform(get("/api/trips/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }


    @Test
    void testGetTrips() {
        //@todo
    }
}