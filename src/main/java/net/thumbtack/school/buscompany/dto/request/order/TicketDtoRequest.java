package net.thumbtack.school.buscompany.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDtoRequest {
    private String orderId;
    private String firstName;
    private String lastName;
    private String passport;
    private String place;
}
