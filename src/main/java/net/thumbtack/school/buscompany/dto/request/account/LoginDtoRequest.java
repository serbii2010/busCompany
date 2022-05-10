package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDtoRequest {
    @NotNull
    private String login;
    @NotNull
    private String password;
}
