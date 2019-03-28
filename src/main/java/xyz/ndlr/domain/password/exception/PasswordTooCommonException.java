package xyz.ndlr.domain.password.exception;

public class PasswordTooCommonException extends NonCompliantPasswordException {
    public PasswordTooCommonException() {
        super("Password is too common.");
    }
}
