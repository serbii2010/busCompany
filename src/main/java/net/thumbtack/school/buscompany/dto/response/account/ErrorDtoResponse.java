package net.thumbtack.school.buscompany.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDtoResponse {
    private String errorCode;
    private String field;
    private String message;
}
