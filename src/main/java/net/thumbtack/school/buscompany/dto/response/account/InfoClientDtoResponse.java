package net.thumbtack.school.buscompany.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoClientDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String phone;
    private String login;
    private String password;
}
