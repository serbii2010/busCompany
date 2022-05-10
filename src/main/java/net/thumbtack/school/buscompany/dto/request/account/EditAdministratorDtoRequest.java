package net.thumbtack.school.buscompany.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
///@todo проверка совпадения поролей
// отсутствующие поля не изменяются
public class EditAdministratorDtoRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String oldPassword;
    private String newPassword;
}
