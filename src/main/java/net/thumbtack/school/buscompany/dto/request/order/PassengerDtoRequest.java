package net.thumbtack.school.buscompany.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDtoRequest {
    private String firstName;
    private String lastName;
    private String passport;
}
