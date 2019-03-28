package xyz.ndlr.infrastructure.security.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;

@Component
public class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 jours
    private static byte[] SECRET;
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${jwt.secret}")
    private void setSecret(String secret) throws UnsupportedEncodingException {
        SECRET = secret.getBytes("UTF-8");
    }

    static void addAuthentication(HttpServletResponse response, String username)
            throws IOException {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        JWTToken token = new JWTToken(JWT);

        // the jwt token is sent both in the http headers and the response body
        response.setContentType("application/json");
        response.addHeader(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_PREFIX, token
                .getToken()));
        response.getWriter().write(token.toJson());
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        Authentication authentication = null;
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token != null) {
            String username = null;
            try {
                username = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getSubject();
            } catch(Exception ignored){}

            if (username != null) {
                authentication = new UsernamePasswordAuthenticationToken(username, null,
                        Collections.emptyList());
            }
        }

        return authentication;
    }
}
