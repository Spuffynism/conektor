package xyz.ndlr.security.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.ndlr.exception.InvalidCredentialsException;
import xyz.ndlr.exception.UserNotFoundException;
import xyz.ndlr.security.auth.AccountCredentials;
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

/**
 * Intercepts http requets before they reach the controllers and attempts to authenticate the user
 */
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

    private Authentication authenticateUser(String identifier, String password)
            throws InvalidCredentialsException, UserNotFoundException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                (identifier, password,
                Collections.emptyList());

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain chain, Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(httpServletResponse, auth.getName());
    }
}
