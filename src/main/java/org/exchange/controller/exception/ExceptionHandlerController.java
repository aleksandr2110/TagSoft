package org.exchange.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.exchange.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleEntityNotFoundException(EntityNotFoundException e) {
        return createError(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private Error createError(HttpStatus code, String message) {
        Error error = new Error();
        error.setCode(Integer.toString(code.value()));
        error.setMessage(message);
        return error;
    }
}
