package net.thumbtack.school.buscompany.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.buscompany.validator.DateFormat;
import net.thumbtack.school.buscompany.validator.DateOrderInDatesTrip;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DateOrderInDatesTrip
public class OrderDtoRequest {
    private int tripId;
    @DateFormat
    private String date;
    private List<PassengerDtoRequest> passengers;
}
