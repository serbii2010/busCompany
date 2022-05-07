package net.thumbtack.school.bascompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
///@todo проверка совпадения поролей
// отсутствующие поля не изменяются
public class EditClientDtoRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String phone;
    private String oldPassword;
    private String newPassword;
}
