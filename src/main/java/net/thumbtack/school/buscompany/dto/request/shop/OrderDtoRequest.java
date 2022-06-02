package net.thumbtack.school.buscompany.dto.request.shop;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoRequest {
    private int tripId;
    private Date date;
    private List<PassengerDtoRequest> passengers;
}
