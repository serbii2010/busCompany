package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.UniqueLogin;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAdminDtoRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String patronymic;
    @NotNull
    private String position;
    @NotNull
    ///@todo проверка уникальности без учета регистра
    ///@todo проверка русские буквы и знак тире
    @UniqueLogin
    private String login;
    @NotNull
    private String password;
}
