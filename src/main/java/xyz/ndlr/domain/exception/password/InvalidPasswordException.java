package xyz.ndlr.domain.exception.password;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Invalid password.");
    }
}
