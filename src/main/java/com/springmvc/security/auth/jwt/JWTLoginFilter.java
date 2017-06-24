package com.springmvc.security.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.model.User;
import com.springmvc.security.auth.AccountCredentials;
import com.springmvc.security.auth.exception.InvalidPasswordException;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
import com.springmvc.service.AuthHolder;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserService userService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds;
        Authentication authentication = null;
        try {
            creds = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
            authentication = authenticateUser(creds);
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return authentication;
    }

    private Authentication authenticateUser(AccountCredentials creds)
            throws InvalidPasswordException {
        return authenticateUser(creds.getUsername(), creds.getPassword());
    }

    // TODO Integrate hashing in authentication
    private Authentication authenticateUser(String username, String password)
            throws InvalidPasswordException {
        User user = userService.loadUserByUsername(username);
        IPasswordHasher argon2Hasher = new Argon2Hasher();
        boolean passwordOK = argon2Hasher.verify(user.getPassword(), password);

        if (!passwordOK)
            throw new InvalidPasswordException("");

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        user.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain chain, Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(httpServletResponse, auth.getName());
    }
}
