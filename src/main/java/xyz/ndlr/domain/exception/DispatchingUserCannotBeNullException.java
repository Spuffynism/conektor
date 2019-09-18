package xyz.ndlr.domain.exception;

public class DispatchingUserCannotBeNullException extends CannotDispatchException {
    public DispatchingUserCannotBeNullException() {
        super("user cannot be null - a message needs a destination");
    }
}
