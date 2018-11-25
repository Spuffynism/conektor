package xyz.ndlr.domain.exception;

public class UsernameTakenException extends Exception {
    public UsernameTakenException(String message) {
        super(message);
    }
}
