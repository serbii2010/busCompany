package net.thumbtack.school.buscompany.helper.dto.request.account;

import net.thumbtack.school.buscompany.dto.request.account.RegistrationAdminDtoRequest;

public class RegistrationAdminDtoRequestHelper {
    public static  RegistrationAdminDtoRequest get(String login) {
        return new RegistrationAdminDtoRequest(
                "имя",
                "фамилия",
                "отчество",
                "director",
                login,
                "password"
        );
    }
}
