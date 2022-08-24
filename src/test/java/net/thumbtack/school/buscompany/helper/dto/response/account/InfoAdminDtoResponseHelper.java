package net.thumbtack.school.buscompany.helper.dto.response.account;

import net.thumbtack.school.buscompany.dto.response.account.InfoAdministratorDtoResponse;

public class InfoAdminDtoResponseHelper {
    public static InfoAdministratorDtoResponse get() {
        return new InfoAdministratorDtoResponse(
                1,
                "имя",
                "фамилия",
                "отчество",
                "director",
                "admin"
        );
    }
}
