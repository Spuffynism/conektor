package xyz.ndlr.security.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xyz.ndlr.exception.InvalidCredentialsException;
import xyz.ndlr.exception.UserNotFoundException;
import xyz.ndlr.model.FacebookMessageConsumer;
import xyz.ndlr.security.auth.AccountCredentials;

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
    private static final Logger logger = Logger.getLogger(JWTLoginFilter.class);

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials credentials;
        Authentication authentication = null;

        try {
            credentials = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), AccountCredentials.class);

            authentication = authenticateUser(credentials.getIdentifier(),
                    credentials.getPassword());
        } catch (Exception e) {
            logger.error(e);
            // We don't tell if it was an invalidCredentialsException or a UserNotFoundException.
            // We only say there was an error.
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return authentication;
    }

    private Authentication authenticateUser(String identifier, String password)
            throws InvalidCredentialsException, UserNotFoundException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                identifier, password, Collections.emptyList());

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
