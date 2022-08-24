package net.thumbtack.school.buscompany.helper.dto.response.account;

import net.thumbtack.school.buscompany.dto.response.account.InfoClientDtoResponse;

public class InfoClientDtoResponseHelper {
    public static InfoClientDtoResponse get() {
        return new InfoClientDtoResponse(
                1,
                "имя-клиента",
                "фамилия",
                "отчество",
                "client@c.ru",
                "88005553535",
                "client"
        );
    }
}
