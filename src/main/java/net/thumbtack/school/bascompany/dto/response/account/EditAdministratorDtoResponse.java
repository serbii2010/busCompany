package net.thumbtack.school.bascompany.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAdministratorDtoResponse {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String password;
    private String userType;
}
