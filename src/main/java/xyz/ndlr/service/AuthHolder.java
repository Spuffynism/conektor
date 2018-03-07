package xyz.ndlr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.entity.User;

@Component
public class AuthHolder {
    private final UserService userService;

    @Autowired
    public AuthHolder(UserService userService) {
        this.userService = userService;
    }

    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public xyz.ndlr.model.entity.User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        String username;
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            username = ((org.springframework.security.core.userdetails.User) principal)
                    .getUsername();
        } else {
            username = (String) principal;
        }

        return userService.loadUserByUsername(username);
    }

    public boolean isMe(int userId) {
        return userId == getUser().getId();
    }

    public boolean isMe(User user) {
        return user != null && user.getId() == getUser().getId();
    }
}
