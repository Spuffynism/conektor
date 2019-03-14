package xyz.ndlr.presentation.rest.error_handling.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.ndlr.domain.exception.EmailTakenException;
import xyz.ndlr.domain.exception.UsernameTakenException;
import xyz.ndlr.domain.exception.password.InvalidPasswordException;

@ControllerAdvice
public class PasswordChangeExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UsernameTakenException.class, EmailTakenException.class,
            InvalidPasswordException.class})
    public void handle() {
    }
}
