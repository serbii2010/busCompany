package net.thumbtack.school.buscompany.dto.request.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.BusExist;
import net.thumbtack.school.buscompany.validator.StationExist;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTripDtoRequest {
    @NotBlank
    @BusExist(message = "Bus not found")
    private String busName;
    @StationExist(message = "Station not found")
    private String fromStation;
    @StationExist(message = "Station not found")
    private String toStation;
    private String start;
    private String duration;
    private int price;
    private ScheduleDto schedule;
    private List<String> dates;
}
