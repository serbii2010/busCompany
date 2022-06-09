package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAdministratorDtoRequest {
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
    @MinPasswordLength
    private String oldPassword;
    @NotBlank
    @MinPasswordLength
    private String newPassword;
}
