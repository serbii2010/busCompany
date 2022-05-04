package net.thumbtack.school.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationAdminDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String userType;
}
