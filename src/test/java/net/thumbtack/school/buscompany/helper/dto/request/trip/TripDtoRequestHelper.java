package net.thumbtack.school.buscompany.helper.dto.request.trip;

import net.thumbtack.school.buscompany.dto.request.trip.ScheduleDtoRequest;
import net.thumbtack.school.buscompany.dto.request.trip.TripDtoRequest;

import java.util.ArrayList;
import java.util.List;

public class TripDtoRequestHelper {
    private static TripDtoRequest getBaseRequest() {
        return new TripDtoRequest(
                "Пазик",
                "Omsk",
                "Новосибирск",
                "12:30",
                "23:51",
                20,
                null,
                null
        );
    }
    public static TripDtoRequest getWithDates() {
        List<String> dates = new ArrayList<>();
        dates.add("2022-12-12");
        dates.add("2022-12-13");
        TripDtoRequest request = getBaseRequest();
        request.setDates(dates);
        return request;
    }

    public static TripDtoRequest getWithScheduleWeek() {
        TripDtoRequest request = getBaseRequest();
        request.setSchedule(new ScheduleDtoRequest("2022-12-31", "2023-01-31", "Fri,Sat"));
        return request;
    }

    public static TripDtoRequest getWithScheduleOdd() {
        TripDtoRequest request = getBaseRequest();
        request.setSchedule(new ScheduleDtoRequest("2022-12-31", "2023-01-31", "odd"));
        return request;
    }

    public static TripDtoRequest getWithScheduleDaily() {
        TripDtoRequest request = getBaseRequest();
        request.setSchedule(new ScheduleDtoRequest("2022-12-31", "2023-01-31", "daily"));
        return request;
    }

    public static TripDtoRequest getWithScheduleEven() {
        TripDtoRequest request = getBaseRequest();
        request.setSchedule(new ScheduleDtoRequest("2022-12-31", "2023-01-31", "even"));
        return request;
    }

    public static TripDtoRequest getWithScheduleDayInMonth() {
        TripDtoRequest request = getBaseRequest();
        request.setSchedule(new ScheduleDtoRequest("2022-12-31", "2023-01-31", "1,13,25,31"));
        return request;
    }


}
