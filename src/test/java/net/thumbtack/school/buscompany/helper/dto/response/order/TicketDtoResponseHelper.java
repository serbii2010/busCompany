package net.thumbtack.school.buscompany.helper.dto.response.order;

import net.thumbtack.school.buscompany.dto.response.order.TicketDtoResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketDtoResponseHelper {
    public static TicketDtoResponse getInsert() {
        return new TicketDtoResponse(
                "1",
                "37_4",
                "ivanov2",
                "ivan2",
                "5501 254691",
                4
        );
    }

    public static List<Integer> getFreePlaces() {
        return new ArrayList<>(Arrays.asList(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                31,
                32,
                33,
                34,
                35,
                36,
                37,
                38,
                39,
                40
        ));
    }

    public static List<Integer> getFreePlaceWithout4() {
        return new ArrayList<>(Arrays.asList(
                1,
                2,
                3,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                31,
                32,
                33,
                34,
                35,
                36,
                37,
                38,
                39,
                40
        ));
    }

    public static List<Integer> getFreePlaceWithout30() {
        return new ArrayList<>(Arrays.asList(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                31,
                32,
                33,
                34,
                35,
                36,
                37,
                38,
                39,
                40
        ));
    }
}
