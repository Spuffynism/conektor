package xyz.ndlr.domain.exception;

import xyz.ndlr.domain.user.Username;

public class UsernameTakenException extends Exception {
    public UsernameTakenException(Username username) {
        super("Username " + username.getValue() + " is already taken");
    }
}
