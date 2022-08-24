package net.thumbtack.school.buscompany.helper.dto.response.account;

import net.thumbtack.school.buscompany.dto.response.account.EditClientDtoResponse;

public class UpdateClientDtoResponseHelper {
    public static EditClientDtoResponse get() {
        return new EditClientDtoResponse(
                "клиент",
                "фамилия",
                "отчество",
                "client@c.ru",
                "88005553535",
                "client"
        );
    }
}
