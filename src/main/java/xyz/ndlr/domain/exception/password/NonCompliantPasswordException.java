package xyz.ndlr.domain.exception.password;

public class NonCompliantPasswordException extends PasswordException {
    public NonCompliantPasswordException() {
        super("New password is invalid.");
    }
}
