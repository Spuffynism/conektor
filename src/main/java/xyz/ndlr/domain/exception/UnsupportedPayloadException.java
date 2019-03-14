package xyz.ndlr.domain.exception;

public class UnsupportedPayloadException extends Exception {
    public UnsupportedPayloadException() {
        super("unsupported payload");
    }
}
