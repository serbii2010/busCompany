package net.thumbtack.school.buscompany.helper.dto.response.order;

import net.thumbtack.school.buscompany.dto.request.order.PassengerDtoRequest;
import net.thumbtack.school.buscompany.dto.response.order.OrderDtoResponse;
import net.thumbtack.school.buscompany.dto.response.order.PassengerDtoResponse;

import java.util.ArrayList;
import java.util.Arrays;

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
                "Пазик",
                "2022-12-31",
                "12:30",
                20,
                40,
                new ArrayList<>(Arrays.asList(passenger1, passenger2))
        );
    }
}
