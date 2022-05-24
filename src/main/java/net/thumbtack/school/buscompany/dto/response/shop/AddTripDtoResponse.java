package net.thumbtack.school.buscompany.dto.response.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTripDtoResponse {
    private int tripId;
    private String fromStation;
    private String toStation;
    private String start;
    private String duration;
    private int price;
    private BusDtoResponse bus;
    private Boolean approved;
    private ScheduleDtoResponse schedule;
    private List<String> dates;
}
