package xyz.ndlr.domain.user;

import org.springframework.stereotype.Component;
import xyz.ndlr.domain.password.HashedPassword;

@Component
public class UserFactory {

    public User create(UserCreationRequest userCreationRequest, HashedPassword hashedPassword) {
        return new User(userCreationRequest.getUsername(), userCreationRequest.getEmail(),
                hashedPassword);
    }
}
