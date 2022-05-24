package net.thumbtack.school.buscompany.dto.request.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTripDtoRequest {
    private String busName;
    private String fromStation;
    private String toStation;
    private String start;
    private String duration;
    private int price;
    private ScheduleDto schedule;
    private List<String> dates;
}
