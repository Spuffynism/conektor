package xyz.ndlr.exception;

public class UnregisteredAccountException extends Exception {
    public UnregisteredAccountException(String message) {
        super(message);
    }
}
