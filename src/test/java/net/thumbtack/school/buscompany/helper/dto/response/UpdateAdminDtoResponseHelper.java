package net.thumbtack.school.buscompany.helper.dto.response;

import net.thumbtack.school.buscompany.dto.response.account.EditAdministratorDtoResponse;

public class UpdateAdminDtoResponseHelper {
    public static EditAdministratorDtoResponse get() {
        return new EditAdministratorDtoResponse(
                "админ",
                "фамилия",
                "отчество",
                "general director",
                "admin"
        );
    }
}
