package net.thumbtack.school.buscompany.dto.request.trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.buscompany.validator.DateFormat;
import net.thumbtack.school.buscompany.validator.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDtoRequest {
    @DateFormat
    private String fromDate;
    @DateFormat
    private String toDate;
    @Period
    private String period;
}
