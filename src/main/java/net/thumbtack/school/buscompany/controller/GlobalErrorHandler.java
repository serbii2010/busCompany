package net.thumbtack.school.buscompany.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.buscompany.dto.response.account.ErrorDtoResponse;
import net.thumbtack.school.buscompany.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
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
        error.getErrors().add(String.format("Error: %s", exception.getErrorCode().getErrorString()));
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyError handleValidation(MethodArgumentNotValidException exc) {
        final MyError error = new MyError();
        exc.getBindingResult().getFieldErrors().forEach(fieldError-> {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse();
            errorDtoResponse.setErrorCode(fieldError.getCode());
            errorDtoResponse.setField(fieldError.getField());
            errorDtoResponse.setMessage(fieldError.getDefaultMessage());
            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonError = mapper.writeValueAsString(errorDtoResponse);
                error.getErrors().add(jsonError);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        exc.getBindingResult().getGlobalErrors().forEach(err-> {
            error.getErrors().add(String.format("global:%s", err.getDefaultMessage()));
        });
        return error;
    }

    public static class MyError {
        private List<String> errors = new ArrayList<>();

        public List<String> getErrors() {
            return errors;
        }

        public void setAllErrors(List<String> Errors) {
            this.errors = Errors;
        }
    }
}