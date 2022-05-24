package net.thumbtack.school.buscompany.dto.request.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private String fromDate;
    private String toDate;
    private String period;
}
