package net.thumbtack.school.buscompany.dto.request.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.IsBusExist;
import net.thumbtack.school.buscompany.validator.IsSetScheduleOrDatesField;
import net.thumbtack.school.buscompany.validator.IsStationExist;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IsSetScheduleOrDatesField
public class CreateTripDtoRequest {
    @NotBlank
    @IsBusExist
    private String busName;
    @IsStationExist
    private String fromStation;
    @IsStationExist
    private String toStation;
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "format field \'HH:MM\'")
    private String start;
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "format field \'HH:MM\'")
    private String duration;
    private int price;
    @Valid
    private ScheduleDto schedule;
    private List<String> dates;
}
