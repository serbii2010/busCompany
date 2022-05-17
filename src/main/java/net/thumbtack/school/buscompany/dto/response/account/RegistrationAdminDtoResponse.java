package net.thumbtack.school.buscompany.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationAdminDtoResponse implements BaseAccountDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String userType;
}
