package xyz.ndlr.presentation.rest.error_handling.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.ndlr.domain.password.exception.PasswordException;

@ControllerAdvice
public class PasswordExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(PasswordException.class)
    public void handle() {
    }
}
