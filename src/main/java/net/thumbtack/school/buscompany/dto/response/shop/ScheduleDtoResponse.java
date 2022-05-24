package net.thumbtack.school.buscompany.dto.response.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDtoResponse {
    private String fromDate;
    private String toDate;
    private String period;
}
