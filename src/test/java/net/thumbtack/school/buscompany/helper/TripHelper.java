package net.thumbtack.school.buscompany.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.helper.dto.request.trip.TripDtoRequestHelper;
import net.thumbtack.school.buscompany.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Getter
@Setter
@Component
public class TripHelper {
    @Autowired
    private DateTripHelper dateTripHelper;

    private Trip trip;
    private Bus bus;
    private Schedule schedule;
    private Station fromStation;
    private Station toStation;

    public void init() {
        bus = new Bus(1, "Пазик", 21);
        fromStation = new Station(1, "omsk");
        toStation = new Station(2, "novosibirsk");

        dateTripHelper.init("2021-12-12");
        Date fromDate = dateTripHelper.getDate();
        dateTripHelper.init("2022-12-12");
        Date toDate = dateTripHelper.getDate();
        schedule = new Schedule(1, fromDate, toDate, "odd", null);
        List<DateTrip> dates = new ArrayList<>();
        trip = new Trip(1, bus, fromStation, toStation, "12:30", "23:51", 30, false, schedule, dates);

        dates.add(dateTripHelper.getDateTrip(trip));
        trip.setDates(dates);
    }

    public static int insertTrip(TripDtoRequest requestTrip, Cookie cookie, MockMvc mvc, ObjectMapper mapper) throws Exception {
        String result = mvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestTrip))
                .cookie(cookie))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(result, TripAdminDtoResponse.class).getTripId();
    }

    public static void approveTrip(int tripId, Cookie cookie, MockMvc mvc) throws Exception {
        mvc.perform(put("/api/trips/" + tripId + "/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie));
    }

    public void generateDefaultTrip(Cookie cookie, MockMvc mvc, ObjectMapper mapper) throws Exception {
        int tripId1 = insertTrip(TripDtoRequestHelper.getWithScheduleWeek(), cookie, mvc, mapper);
        approveTrip(tripId1, cookie, mvc);
        int tripId2 = insertTrip(TripDtoRequestHelper.getUpdateToScheduleEven(), cookie, mvc, mapper);
        approveTrip(tripId2, cookie, mvc);
    }
}
