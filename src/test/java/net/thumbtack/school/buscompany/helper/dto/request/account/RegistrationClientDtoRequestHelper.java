package net.thumbtack.school.buscompany.helper.dto.request.account;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationClientDtoRequest;

public class RegistrationClientDtoRequestHelper {
    public static RegistrationClientDtoRequest get() {
        return new RegistrationClientDtoRequest(
                "имя-клиента",
                "фамилия",
                "отчество",
                "client@c.ru",
                "8-800-555-3535",
                "client",
                "password"
        );
    }
}
