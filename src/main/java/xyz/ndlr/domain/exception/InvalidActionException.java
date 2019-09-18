package xyz.ndlr.domain.exception;

public class InvalidActionException extends CannotDispatchException {
    public InvalidActionException() {
        super("invalid action");
    }
}
