package net.thumbtack.school.buscompany.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDtoResponse {
    private String orderId;
    private String ticket;
    private String lastName;
    private String firstName;
    private String passport;
    private int place;
}
