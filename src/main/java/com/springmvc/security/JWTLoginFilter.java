package com.springmvc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.model.user.User;
import com.springmvc.model.user.UserService;
import com.springmvc.security.auth.AccountCredentials;
import com.springmvc.security.auth.exception.InvalidPasswordException;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
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

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = null;
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

    public Authentication authenticateUser(AccountCredentials creds)
            throws InvalidPasswordException {
        return authenticateUser(creds.getUsername(), creds.getPassword());
    }

    public Authentication authenticateUser(String username, String password)
            throws InvalidPasswordException {
        IPasswordHasher argon2Hasher = new Argon2Hasher();
        User user = new UserService().loadUserByUsername(username);
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
