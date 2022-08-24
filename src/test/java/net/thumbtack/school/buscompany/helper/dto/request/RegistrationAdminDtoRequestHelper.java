package net.thumbtack.school.buscompany.helper.dto.request;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;

public class RegistrationAdminDtoRequestHelper {
    public static  RegistrationAdminDtoRequest get() {
        return new RegistrationAdminDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                "admin",
                "password"
        );
    }
}
