package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;
import net.thumbtack.school.buscompany.validator.UniqueLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationClientDtoRequest {
    @NotBlank
    @MaxNameLength
    @Pattern(regexp = "^[А-Яа-я \\-]+$", message = "имя содержит недопустимые симвомы")
    private String firstName;
    @NotBlank
    @MaxNameLength
    @Pattern(regexp = "^[А-Яа-я \\-]+$", message = "фамилия содержит недопустимые симвомы")
    private String lastName;
    @MaxNameLength
    @Pattern(regexp = "^[А-Яа-я \\-]+$", message = "отчество содержит недопустимые симвомы")
    private String patronymic;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "номер телефона введен неверно")
    private String phone;
    @NotBlank
    @UniqueLogin
    @MaxNameLength
    @Pattern(regexp = "^[0-9A-Za-zА-Яа-я]+", message = "Используются недопустимые символы")
    private String login;
    @MinPasswordLength
    private String password;
}
