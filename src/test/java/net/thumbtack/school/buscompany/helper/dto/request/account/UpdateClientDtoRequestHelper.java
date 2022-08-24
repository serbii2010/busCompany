package net.thumbtack.school.buscompany.helper.dto.request.account;

import net.thumbtack.school.buscompany.dto.request.account.EditClientDtoRequest;

public class UpdateClientDtoRequestHelper {
    public static EditClientDtoRequest get() {
        return new EditClientDtoRequest(
                "клиент",
                "фамилия",
                "отчество",
                "client@c.ru",
                "8-800-555-3535",
                "password",
                "password"
        );
    }
}
