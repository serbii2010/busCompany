package net.thumbtack.school.buscompany.helper.dto.request.order;

import net.thumbtack.school.buscompany.dto.request.order.OrderDtoRequest;
import net.thumbtack.school.buscompany.dto.request.order.PassengerDtoRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderDtoRequestHelper {
    public static OrderDtoRequest getDtoInsert() {
        PassengerDtoRequest passenger1 = new PassengerDtoRequest(
                "ivan",
                "ivanov",
                "5501 254691"
        );
        PassengerDtoRequest passenger2 = new PassengerDtoRequest(
                "aleksandr",
                "Александров",
                "5128 46732135"
        );
        return new OrderDtoRequest(
                2,
                "2022-12-31",
                new ArrayList<>(Arrays.asList(passenger1, passenger2))
        );
    }

    public static OrderDtoRequest getDtoInsertTwo() {
        PassengerDtoRequest passenger1 = new PassengerDtoRequest(
                "ivan2",
                "ivanov2",
                "5501 254691"
        );
        PassengerDtoRequest passenger2 = new PassengerDtoRequest(
                "aleksandr2",
                "Александров2",
                "5128 46732135"
        );
        return new OrderDtoRequest(
                2,
                "2022-12-31",
                new ArrayList<>(Arrays.asList(passenger1, passenger2))
        );
    }
}
