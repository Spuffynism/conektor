package xyz.ndlr.presentation.rest.error_handling.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.ndlr.domain.exception.UnauthorizedException;

@ControllerAdvice
public class UnauthorizedExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public void handle() {
    }
}
