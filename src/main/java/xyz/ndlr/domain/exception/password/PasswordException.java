package xyz.ndlr.domain.exception.password;

public abstract class PasswordException extends Exception {
    PasswordException(String message) {
        super(message);
    }
}
