package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.MaxNameLength;
import net.thumbtack.school.buscompany.validator.MinPasswordLength;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditClientDtoRequest {
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
    @MinPasswordLength
    private String oldPassword;
    @NotBlank
    @MinPasswordLength
    private String newPassword;
}
