package net.thumbtack.school.buscompany.dto.request.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.DateFormat;
import net.thumbtack.school.buscompany.validator.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    @DateFormat
    private String fromDate;
    @DateFormat
    private String toDate;
    @Period
    private String period;
}
