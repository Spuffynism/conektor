package xyz.ndlr.presentation.rest.error_handling.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;

@ControllerAdvice
public class InvalidFacebookVerificationTokenExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidFacebookVerificationTokenException.class)
    public void handle(){}
}
