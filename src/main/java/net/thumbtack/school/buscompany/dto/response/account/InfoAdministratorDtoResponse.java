package net.thumbtack.school.buscompany.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoAdministratorDtoResponse implements BaseAccountInfoDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String userType;
}
