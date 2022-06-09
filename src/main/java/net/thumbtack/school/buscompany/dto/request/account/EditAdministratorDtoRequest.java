package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAdministratorDtoRequest {
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
    @MinPasswordLength
    private String oldPassword;
    @NotBlank
    @MinPasswordLength
    private String newPassword;
}
