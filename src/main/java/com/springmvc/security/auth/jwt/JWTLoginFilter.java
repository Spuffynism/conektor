package com.springmvc.security.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.exception.InvalidCredentialsException;
import com.springmvc.exception.InvalidPasswordException;
import com.springmvc.exception.UserNotFoundException;
import com.springmvc.model.User;
import com.springmvc.security.auth.AccountCredentials;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
import com.springmvc.service.UserService;
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
        AccountCredentials creds;
        Authentication authentication = null;
        try {
            creds = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
            authentication = authenticateUser(creds.getIdentifier(), creds.getPassword());
        } catch (Exception e) {
            // We don't tell if it was an invalidCredentialsException or a UserNotFoundException.
            // We only say there was an error.
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return authentication;
    }

    // TODO Integrate hashing in authentication
    private Authentication authenticateUser(String identifier, String password)
            throws InvalidCredentialsException, UserNotFoundException {
        User user = new UserService().loadByIdentifier(identifier);
        IPasswordHasher argon2Hasher = new Argon2Hasher();
        boolean passwordOK = argon2Hasher.verify(user.getPassword(), password);

        if (!passwordOK)
            throw new InvalidCredentialsException("Invalid credentials");

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        identifier,
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
