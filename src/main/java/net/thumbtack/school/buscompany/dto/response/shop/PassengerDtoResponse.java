package net.thumbtack.school.buscompany.dto.response.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDtoResponse {
    private String firstName;
    private String lastName;
    private String passport;
}
