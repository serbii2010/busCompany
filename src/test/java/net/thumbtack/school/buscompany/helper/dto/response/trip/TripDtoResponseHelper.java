package net.thumbtack.school.buscompany.helper.dto.response.trip;

import net.thumbtack.school.buscompany.dto.response.trip.BusDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.ScheduleDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripAdminDtoResponse;
import net.thumbtack.school.buscompany.dto.response.trip.TripClientDtoResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripDtoResponseHelper {
    private static TripAdminDtoResponse getBaseDtoResponse() {
        TripAdminDtoResponse response = new TripAdminDtoResponse();
        response.setTripId(1);
        response.setFromStation("Omsk");
        response.setToStation("Новосибирск");
        response.setStart("12:30");
        response.setDuration("23:51");
        response.setPrice(20);
        response.setBus(new BusDtoResponse("Пазик", 21));
        response.setApproved(false);
        response.setSchedule(null);
        response.setDates(null);
        return response;
    }

    private static TripClientDtoResponse getBaseDtoResponseByClient() {
        TripClientDtoResponse response = new TripClientDtoResponse();
        response.setTripId(1);
        response.setFromStation("Omsk");
        response.setToStation("Новосибирск");
        response.setStart("12:30");
        response.setDuration("23:51");
        response.setPrice(20);
        response.setBus(new BusDtoResponse("Пазик", 21));
        response.setSchedule(null);
        response.setDates(null);
        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();
        List<String> dates = new ArrayList<>();
        dates.add("2022-12-12");
        dates.add("2022-12-13");
        response.setDates(dates);
        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleWeek() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "Fri,Sat"));
        List<String> dates = new ArrayList<>();
        dates.add("2022-12-31");
        dates.add("2023-01-06");
        dates.add("2023-01-07");
        dates.add("2023-01-13");
        dates.add("2023-01-14");
        dates.add("2023-01-20");
        dates.add("2023-01-21");
        dates.add("2023-01-27");
        dates.add("2023-01-28");
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleWeekFilterToDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "Fri,Sat"));
        List<String> dates = new ArrayList<>();
        dates.add("2022-12-31");
        dates.add("2023-01-06");
        dates.add("2023-01-07");
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleWeekFilterDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "Fri,Sat"));
        List<String> dates = new ArrayList<>();
        dates.add("2023-01-13");
        dates.add("2023-01-14");
        dates.add("2023-01-20");
        dates.add("2023-01-21");
        dates.add("2023-01-27");
        dates.add("2023-01-28");
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleWeekFilterFromToDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "Fri,Sat"));
        List<String> dates = new ArrayList<>();
        dates.add("2023-01-13");
        dates.add("2023-01-14");
        dates.add("2023-01-20");
        response.setDates(dates);

        return response;
    }

    public static TripClientDtoResponse getDtoInsertWithScheduleWeekByClient() {
        TripClientDtoResponse response = getBaseDtoResponseByClient();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "Fri,Sat"));
        List<String> dates = new ArrayList<>();
        dates.add("2022-12-31");
        dates.add("2023-01-06");
        dates.add("2023-01-07");
        dates.add("2023-01-13");
        dates.add("2023-01-14");
        dates.add("2023-01-20");
        dates.add("2023-01-21");
        dates.add("2023-01-27");
        dates.add("2023-01-28");
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleOdd() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "odd"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2022-12-31",
                "2023-01-01",
                "2023-01-03",
                "2023-01-05",
                "2023-01-07",
                "2023-01-09",
                "2023-01-11",
                "2023-01-13",
                "2023-01-15",
                "2023-01-17",
                "2023-01-19",
                "2023-01-21",
                "2023-01-23",
                "2023-01-25",
                "2023-01-27",
                "2023-01-29",
                "2023-01-31"));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleDaily() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "daily"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2022-12-31",
                "2023-01-01",
                "2023-01-02",
                "2023-01-03",
                "2023-01-04",
                "2023-01-05",
                "2023-01-06",
                "2023-01-07",
                "2023-01-08",
                "2023-01-09",
                "2023-01-10",
                "2023-01-11",
                "2023-01-12",
                "2023-01-13",
                "2023-01-14",
                "2023-01-15",
                "2023-01-16",
                "2023-01-17",
                "2023-01-18",
                "2023-01-19",
                "2023-01-20",
                "2023-01-21",
                "2023-01-22",
                "2023-01-23",
                "2023-01-24",
                "2023-01-25",
                "2023-01-26",
                "2023-01-27",
                "2023-01-28",
                "2023-01-29",
                "2023-01-30",
                "2023-01-31"));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleEven() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "even"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2023-01-02",
                "2023-01-04",
                "2023-01-06",
                "2023-01-08",
                "2023-01-10",
                "2023-01-12",
                "2023-01-14",
                "2023-01-16",
                "2023-01-18",
                "2023-01-20",
                "2023-01-22",
                "2023-01-24",
                "2023-01-26",
                "2023-01-28",
                "2023-01-30"
        ));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleEvenFilterToDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "even"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2023-01-02",
                "2023-01-04",
                "2023-01-06",
                "2023-01-08"
        ));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleEvenFilterDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "even"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2023-01-08",
                "2023-01-10",
                "2023-01-12",
                "2023-01-14",
                "2023-01-16",
                "2023-01-18",
                "2023-01-20",
                "2023-01-22",
                "2023-01-24",
                "2023-01-26",
                "2023-01-28",
                "2023-01-30"
        ));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleEvenFilterFromToDates() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "even"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2023-01-08",
                "2023-01-10",
                "2023-01-12",
                "2023-01-14",
                "2023-01-16",
                "2023-01-18",
                "2023-01-20"
        ));
        response.setDates(dates);

        return response;
    }

    public static TripClientDtoResponse getDtoInsertWithScheduleEvenByClient() {
        TripClientDtoResponse response = getBaseDtoResponseByClient();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "even"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2023-01-02",
                "2023-01-04",
                "2023-01-06",
                "2023-01-08",
                "2023-01-10",
                "2023-01-12",
                "2023-01-14",
                "2023-01-16",
                "2023-01-18",
                "2023-01-20",
                "2023-01-22",
                "2023-01-24",
                "2023-01-26",
                "2023-01-28",
                "2023-01-30"
        ));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoInsertWithScheduleDayInMonth() {
        TripAdminDtoResponse response = getBaseDtoResponse();

        response.setSchedule(new ScheduleDtoResponse("2022-12-31", "2023-01-31", "1,13,25,31"));
        List<String> dates = new ArrayList<>(Arrays.asList(
                "2022-12-31",
                "2023-01-01",
                "2023-01-13",
                "2023-01-25",
                "2023-01-31"
        ));
        response.setDates(dates);

        return response;
    }

    public static TripAdminDtoResponse getDtoUpdateWithEven() {
        TripAdminDtoResponse response = getDtoInsertWithScheduleEven();
        response.setBus(new BusDtoResponse("Ikarus", 40));
        response.setToStation("Новосибирск");
        response.setFromStation("Omsk");
        response.setStart("23:51");
        response.setDuration("12:30");
        response.setPrice(30);
        return response;
    }

    public static TripAdminDtoResponse getDtoUpdateApproveWithWeek() {
        TripAdminDtoResponse response = getDtoInsertWithScheduleWeek();
        response.setApproved(true);
        return response;
    }
}
