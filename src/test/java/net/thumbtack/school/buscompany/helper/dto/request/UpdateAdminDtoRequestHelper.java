package net.thumbtack.school.buscompany.helper.dto.request;

import net.thumbtack.school.buscompany.dto.request.account.EditAdministratorDtoRequest;

public class UpdateAdminDtoRequestHelper {
    public static EditAdministratorDtoRequest get() {
        return new EditAdministratorDtoRequest(
                "админ",
                "фамилия",
                "отчество",
                "general director",
                "password",
                "password"
        );
    }
}
