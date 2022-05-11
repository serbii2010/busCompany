package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;
import net.thumbtack.school.buscompany.validator.UniqueLogin;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAdminDtoRequest {
    @NotBlank
    @MaxNameLength
    private String firstName;
    @NotBlank
    @MaxNameLength
    private String lastName;
    @MaxNameLength
    private String patronymic;
    @NotBlank
    private String position;
    @NotBlank
    @MaxNameLength
    ///@todo проверка уникальности без учета регистра
    ///@todo проверка русские буквы и знак тире
    @UniqueLogin
    private String login;
    @MinPasswordLength
    private String password;
}
