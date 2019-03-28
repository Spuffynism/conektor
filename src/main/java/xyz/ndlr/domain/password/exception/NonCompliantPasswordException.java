package xyz.ndlr.domain.password.exception;

public class NonCompliantPasswordException extends PasswordException {
    public NonCompliantPasswordException() {
        super("New password is invalid.");
    }

    public NonCompliantPasswordException(String message) {
        super(message);
    }
}
