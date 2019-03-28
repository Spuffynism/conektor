package xyz.ndlr.domain.password.exception;

public class NonMatchingNewPasswordAndConfirmationException extends NonCompliantPasswordException {
    public NonMatchingNewPasswordAndConfirmationException() {
        super("The current password is not right.");
    }
}
