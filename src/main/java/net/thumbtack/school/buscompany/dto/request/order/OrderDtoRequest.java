package net.thumbtack.school.buscompany.dto.request.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoRequest {
    //@todo добавить валидацию
    private int tripId;
    private Date date;
    private List<PassengerDtoRequest> passengers;
}
