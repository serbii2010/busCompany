package net.thumbtack.school.buscompany.dto.request.order;

import lombok.*;
import net.thumbtack.school.buscompany.validator.DateFormat;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoRequest {
    private int tripId;
    @DateFormat
    private String date;
    private List<PassengerDtoRequest> passengers;
}
