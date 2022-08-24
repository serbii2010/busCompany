package net.thumbtack.school.buscompany.helper.dto.response.account;

import net.thumbtack.school.buscompany.dto.response.account.RegistrationClientDtoResponse;

public class RegistrationClientDtoResponseHelper {
    public static RegistrationClientDtoResponse get() {
        return new RegistrationClientDtoResponse(
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
