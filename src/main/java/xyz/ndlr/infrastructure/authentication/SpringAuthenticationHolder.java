package xyz.ndlr.infrastructure.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.AuthenticationHolder;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.domain.user.Username;
import xyz.ndlr.infrastructure.persistence.QueryExecutorUserRepository;

@Component
public class SpringAuthenticationHolder implements AuthenticationHolder {
    private final QueryExecutorUserRepository userRepository;

    @Autowired
    public SpringAuthenticationHolder(QueryExecutorUserRepository userRepository) {
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

        return userRepository.get(Username.from(username));
    }

    @Override
    public boolean isMe(UserId userId) {
        return userId.equals(getUser().getId());
    }

    @Override
    public boolean isMe(User user) {
        return user != null && user.getId().equals(getUser().getId());
    }
}
