package xyz.ndlr.exception;


public class EmailTakenException extends Exception {
    public EmailTakenException(String message) {
        super(message);
    }
}
