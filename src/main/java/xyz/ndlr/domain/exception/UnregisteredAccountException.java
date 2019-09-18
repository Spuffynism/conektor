package xyz.ndlr.domain.exception;

public class UnregisteredAccountException extends Exception {
    public UnregisteredAccountException() {
        super("unrecognized facebook account - are you registered?");
    }
}
