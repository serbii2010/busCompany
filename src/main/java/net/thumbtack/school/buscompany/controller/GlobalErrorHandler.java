package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public MyError handleDbError(ServerException exception) {
        final MyError error = new MyError();
        error.getAllErrors().add(String.format("Error: %s", exception.getErrorCode().getErrorString()));
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handleValidation(MethodArgumentNotValidException exc) {
        final MyError error = new MyError();
        exc.getBindingResult().getFieldErrors().forEach(fieldError-> {
            error.getAllErrors().add(String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        exc.getBindingResult().getGlobalErrors().forEach(err-> {
            error.getAllErrors().add(String.format("global:%s", err.getDefaultMessage()));
        });
        return error;
    }

    public static class MyError {
        private List<String> allErrors = new ArrayList<>();

        public List<String> getAllErrors() {
            return allErrors;
        }

        public void setAllErrors(List<String> allErrors) {
            this.allErrors = allErrors;
        }
    }
}