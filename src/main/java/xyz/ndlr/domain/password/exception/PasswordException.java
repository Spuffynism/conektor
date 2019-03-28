package xyz.ndlr.domain.password.exception;

public abstract class PasswordException extends Exception {
    PasswordException(String message) {
        super(message);
    }
}
