package xyz.ndlr.domain.password.exception;

public class InvalidPasswordLengthException extends NonCompliantPasswordException {
    public InvalidPasswordLengthException(int length) {
        super("Invalid password length '" + length + "'");
    }
}
