package xyz.ndlr.domain.exception;

import xyz.ndlr.domain.user.UserId;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(UserId userId) {
        super("User " + userId.getValue() + " not found");
    }
}
