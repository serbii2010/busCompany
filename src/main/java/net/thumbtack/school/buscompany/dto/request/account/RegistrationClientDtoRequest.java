package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;
import net.thumbtack.school.buscompany.validator.UniqueLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationClientDtoRequest {
    @NotBlank
    @MaxNameLength
    private String firstName;
    @NotBlank
    @MaxNameLength
    private String lastName;
    @MaxNameLength
    private String patronymic;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    ///@todo проверка на телефон
    private String phone;
    @NotBlank
    @UniqueLogin
    @MaxNameLength
    ///@todo проверка русские буквы и знак тире
    private String login;
    @MinPasswordLength
    private String password;
}
