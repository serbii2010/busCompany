package net.thumbtack.school.buscompany.helper.dto.request.order;

import net.thumbtack.school.buscompany.dto.request.order.TicketDtoRequest;

public class TicketDtoRequestHelper {
    public static TicketDtoRequest getInsert() {
        return new TicketDtoRequest(
                "1",
                "ivan2",
                "ivanov2",
                "5501 254691",
                "4"
        );
    }

    public static TicketDtoRequest getInsertPlace30() {
        return new TicketDtoRequest(
                "1",
                "ivan2",
                "ivanov2",
                "5501 254691",
                "30"
        );
    }
}
