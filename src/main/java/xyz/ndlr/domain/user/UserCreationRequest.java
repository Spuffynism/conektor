package xyz.ndlr.domain.user;

import xyz.ndlr.domain.Email;
import xyz.ndlr.domain.password.Password;

public class UserCreationRequest {

    private Username username;
    private Email email;
    private Password password;

    public Username getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
