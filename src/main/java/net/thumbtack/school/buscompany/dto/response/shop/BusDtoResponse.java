package net.thumbtack.school.buscompany.dto.response.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDtoResponse {
    private String busName;
    private int placeCount;

}
