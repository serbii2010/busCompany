package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;
import net.thumbtack.school.buscompany.validator.UniqueLogin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAdminDtoRequest {
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
    private String position;
    @NotBlank
    @MaxNameLength
    @UniqueLogin
    @Pattern(regexp = "^[0-9A-Za-zА-Яа-я]+", message = "Используются недопустимые символы")
    private String login;
    @MinPasswordLength
    private String password;
}
