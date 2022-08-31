package net.thumbtack.school.buscompany.dto.request.order;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TicketDtoRequest {
    private String orderId;
    private String firstName;
    private String lastName;
    private String passport;
    private String place;
}
