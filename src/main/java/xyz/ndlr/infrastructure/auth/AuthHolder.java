package xyz.ndlr.infrastructure.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.IAuthHolder;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.domain.user.Username;
import xyz.ndlr.repository.UserRepository;

@Component
public class AuthHolder implements IAuthHolder {
    private final UserRepository userRepository;

    @Autowired
    public AuthHolder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        String username;
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            username = ((org.springframework.security.core.userdetails.User) principal)
                    .getUsername();
        } else {
            username = (String) principal;
        }

        return userRepository.get(new Username(username));
    }

    @Override
    public boolean isMe(UserId userId) {
        return userId.getValue() == getUser().getId();
    }

    @Override
    public boolean isMe(User user) {
        return user != null && user.getId() == getUser().getId();
    }
}
