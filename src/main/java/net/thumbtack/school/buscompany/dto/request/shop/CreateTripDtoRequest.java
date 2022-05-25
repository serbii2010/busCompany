package net.thumbtack.school.buscompany.dto.request.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.StationExist;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTripDtoRequest {
    private String busName;
    @StationExist(message = "station not found")
    private String fromStation;
    @StationExist(message = "station not found")
    private String toStation;
    private String start;
    private String duration;
    private int price;
    private ScheduleDto schedule;
    private List<String> dates;
}
