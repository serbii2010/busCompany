package net.thumbtack.school.buscompany.dto.response;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDtoResponse {
    private String maxNameLength;
    private String minPasswordLength;
}
