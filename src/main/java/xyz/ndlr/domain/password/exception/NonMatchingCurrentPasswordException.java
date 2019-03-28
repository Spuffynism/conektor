package xyz.ndlr.domain.password.exception;

public class NonMatchingCurrentPasswordException extends PasswordException {
    public NonMatchingCurrentPasswordException() {
        super("Current password is wrong.");
    }
}
