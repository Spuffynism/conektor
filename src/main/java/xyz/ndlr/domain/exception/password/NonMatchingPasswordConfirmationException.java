package xyz.ndlr.domain.exception.password;

public class NonMatchingPasswordConfirmationException extends PasswordException {
    public NonMatchingPasswordConfirmationException() {
        super("The current password is not right.");
    }
}
