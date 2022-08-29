package net.thumbtack.school.buscompany.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDtoResponse {
    private String errorCode;
    private String field;
    private String message;
}
