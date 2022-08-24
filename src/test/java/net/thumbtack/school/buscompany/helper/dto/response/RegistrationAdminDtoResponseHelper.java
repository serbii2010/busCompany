package net.thumbtack.school.buscompany.helper.dto.response;

import net.thumbtack.school.buscompany.dto.response.account.RegistrationAdminDtoResponse;

public class RegistrationAdminDtoResponseHelper {
    public static RegistrationAdminDtoResponse get() {
        return new RegistrationAdminDtoResponse(
                1,
                "имя",
                "фамилия",
                "отчество",
                "director",
                "admin"
        );
    }
}
