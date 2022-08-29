package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.dto.response.account.ErrorDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerErrorCode;
import net.thumbtack.school.buscompany.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handleMyServerError(ServerException exception) {
        final MyError error = new MyError();
        ErrorDtoResponse dtoResponse = new ErrorDtoResponse();
        dtoResponse.setErrorCode(exception.getErrorCode().getErrorString());
        dtoResponse.setMessage(exception.getErrorCode().getErrorString());
        error.getErrors().add(dtoResponse);
        return error;
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handleCookie(MissingRequestCookieException exception) {
        final MyError error = new MyError();
        ErrorDtoResponse dtoResponse = new ErrorDtoResponse();
        dtoResponse.setErrorCode(ServerErrorCode.BAD_FIELD_COOKIE.getErrorString());
        dtoResponse.setMessage(exception.getMessage());
        dtoResponse.setField(exception.getCookieName());
        error.getErrors().add(dtoResponse);
        return error;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handleValidation(MethodArgumentNotValidException exc) {
        final MyError error = new MyError();
        exc.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse();
            errorDtoResponse.setErrorCode(fieldError.getCode());
            errorDtoResponse.setField(fieldError.getField());
            errorDtoResponse.setMessage(fieldError.getDefaultMessage());
            error.getErrors().add(errorDtoResponse);
        });
        exc.getBindingResult().getGlobalErrors().forEach(err -> {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse();
            errorDtoResponse.setErrorCode(err.getCode());
            errorDtoResponse.setField("Global");
            errorDtoResponse.setMessage(err.getDefaultMessage());
            error.getErrors().add(errorDtoResponse);
        });
        return error;
    }

    public static class MyError {
        private List<ErrorDtoResponse> errors = new ArrayList<>();

        public List<ErrorDtoResponse> getErrors() {
            return errors;
        }

        public void setAllErrors(List<ErrorDtoResponse> Errors) {
            this.errors = Errors;
        }
    }
}