package xyz.ndlr.domain.exception;

public class UnregisteredAccountException extends Exception {
    public UnregisteredAccountException(String message) {
        super(message);
    }
}
