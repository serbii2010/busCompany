package net.thumbtack.school.buscompany.helper.dto.request.account;

import net.thumbtack.school.buscompany.dto.request.account.LoginDtoRequest;

public class LoginClientDtoRequestHelper {
    public static LoginDtoRequest get() {
        return new LoginDtoRequest(
                "client",
                "password"
        );
    }
}
