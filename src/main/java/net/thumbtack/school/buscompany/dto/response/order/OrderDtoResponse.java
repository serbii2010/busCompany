package net.thumbtack.school.buscompany.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoResponse {
    private int orderId;
    private int tripId;
    private String fromStation;
    private String toStation;
    private String busName;
    private String date;
    private String start;
    private String duration;
    private int price;
    private int totalPrice;
    List<PassengerDtoResponse> passengers;
}
