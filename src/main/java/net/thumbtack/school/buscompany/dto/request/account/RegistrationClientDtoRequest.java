package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationClientDtoRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String patronymic;
    @NotNull
    @Email
    private String email;
    @NotNull
    ///@todo проверка на телефон
    private String phone;
    @NotNull
    ///@todo проверка уникальности без учета регистра
    ///@todo проверка русские буквы и знак тире
    private String login;
    @NotNull
    private String password;
}
