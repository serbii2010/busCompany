package net.thumbtack.school.buscompany.helper.dto.response;

import net.thumbtack.school.buscompany.controller.GlobalErrorHandler;
import net.thumbtack.school.buscompany.dto.response.account.ErrorDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;

import java.util.ArrayList;
import java.util.Collections;

public class ErrorDtoResponseHelper {
    public static GlobalErrorHandler.MyError getDtoResponseError(ServerErrorCode errorCode, String field, String message) {
        GlobalErrorHandler.MyError error = new GlobalErrorHandler.MyError();
        error.setAllErrors(new ArrayList<>(Collections.singletonList(new ErrorDtoResponse(
                errorCode.getErrorString(),
                field,
                message
        ))));
        return error;
    }

    public static GlobalErrorHandler.MyError getDtoResponseError(ServerErrorCode errorCode, String message) {
        GlobalErrorHandler.MyError error = new GlobalErrorHandler.MyError();
        error.setAllErrors(new ArrayList<>(Collections.singletonList(new ErrorDtoResponse(
                errorCode.getErrorString(),
                null,
                message
        ))));
        return error;
    }

    public static GlobalErrorHandler.MyError getDtoResponseError(ServerErrorCode errorCode) {
        GlobalErrorHandler.MyError error = new GlobalErrorHandler.MyError();
        error.setAllErrors(new ArrayList<>(Collections.singletonList(new ErrorDtoResponse(
                errorCode.getErrorString(),
                null,
                errorCode.getErrorString()
        ))));
        return error;
    }
}
