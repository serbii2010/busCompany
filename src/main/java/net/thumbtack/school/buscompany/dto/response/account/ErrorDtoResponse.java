package net.thumbtack.school.buscompany.dto.response.account;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDtoResponse {
    private String errorCode;
    private String field;
    private String message;
}
