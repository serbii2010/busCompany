package net.thumbtack.school.buscompany.helper.dto.response.order;

import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.PassengerDtoResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDtoResponseHelper {
    public static OrderDtoResponse getResponse() {
        PassengerDtoResponse passenger1 = new PassengerDtoResponse(
                "ivan",
                "ivanov",
                "5501 254691"
        );
        PassengerDtoResponse passenger2 = new PassengerDtoResponse(
                "aleksandr",
                "Александров",
                "5128 46732135"
        );
        return new OrderDtoResponse(
                1,
                2,
                "Omsk",
                "Новосибирск",
                "Ikarus",
                "2023-01-08",
                "23:51",
                30,
                60,
                new ArrayList<>(Arrays.asList(passenger1, passenger2))
        );
    }

    public static List<OrderDtoResponse> getResponseListAll() {
        List<OrderDtoResponse> responses = new ArrayList<>();
        PassengerDtoResponse passenger1 = new PassengerDtoResponse(
                "ivan",
                "ivanov",
                "5501 254691"
        );
        PassengerDtoResponse passenger2 = new PassengerDtoResponse(
                "aleksandr",
                "Александров",
                "5128 46732135"
        );
        responses.add( new OrderDtoResponse(
                1,
                2,
                "Omsk",
                "Новосибирск",
                "Ikarus",
                "2023-01-08",
                "23:51",
                30,
                60,
                new ArrayList<>(Arrays.asList(passenger1, passenger2))
        ));

        PassengerDtoResponse passenger21 = new PassengerDtoResponse(
                "ivan2",
                "ivanov2",
                "5501 254691"
        );
        PassengerDtoResponse passenger22 = new PassengerDtoResponse(
                "aleksandr2",
                "Александров2",
                "5128 46732135"
        );
        responses.add(new OrderDtoResponse(
                2,
                1,
                "Omsk",
                "Новосибирск",
                "Пазик",
                "2023-01-13",
                "12:30",
                20,
                40,
                new ArrayList<>(Arrays.asList(passenger21, passenger22))
        ));

        return responses;
    }

    public static List<OrderDtoResponse> getResponseListFirst() {
        List<OrderDtoResponse> responses = new ArrayList<>();
        PassengerDtoResponse passenger1 = new PassengerDtoResponse(
                "ivan",
                "ivanov",
                "5501 254691"
        );
        PassengerDtoResponse passenger2 = new PassengerDtoResponse(
                "aleksandr",
                "Александров",
                "5128 46732135"
        );
        responses.add( new OrderDtoResponse(
                1,
                2,
                "Omsk",
                "Новосибирск",
                "Ikarus",
                "2023-01-08",
                "23:51",
                30,
                60,
                new ArrayList<>(Arrays.asList(passenger1, passenger2))
        ));

        return responses;
    }

    public static List<OrderDtoResponse> getResponseListTwo() {
        List<OrderDtoResponse> responses = new ArrayList<>();

        PassengerDtoResponse passenger21 = new PassengerDtoResponse(
                "ivan2",
                "ivanov2",
                "5501 254691"
        );
        PassengerDtoResponse passenger22 = new PassengerDtoResponse(
                "aleksandr2",
                "Александров2",
                "5128 46732135"
        );
        responses.add(new OrderDtoResponse(
                2,
                1,
                "Omsk",
                "Новосибирск",
                "Пазик",
                "2023-01-13",
                "12:30",
                20,
                40,
                new ArrayList<>(Arrays.asList(passenger21, passenger22))
        ));

        return responses;
    }
}
