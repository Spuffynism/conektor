package com.springmvc.service;

import com.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthHolder {
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthHolder(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public User getUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return (User) userDetailsService.loadUserByUsername(username);
    }

    public boolean isMe(int userId) {
        return userId == getUser().getId();
    }

    public boolean isMe(User user) {
        return user != null && user.getId() == getUser().getId();
    }
}
