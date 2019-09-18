package xyz.ndlr.domain.exception;


import xyz.ndlr.domain.user.Email;

public class EmailTakenException extends Exception {
    public EmailTakenException(Email email) {
        super("Email " + email.getValue() + " is already taken.");
    }
}
