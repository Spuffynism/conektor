package xyz.ndlr.domain.exception.password;

public class PasswordChangeAttemptCountReachedException extends PasswordException {
    public PasswordChangeAttemptCountReachedException() {
        super("The maximum allowed password change attempts has been reached.");
    }
}
