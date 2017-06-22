package com.springmvc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 jours
    private static final String SECRET = "secret-securite-tp4";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse response, String username)
            throws IOException {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes("UTF-8"))
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + JWT + "\"}");
    }

    static Authentication getAuthentication(HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey(SECRET.getBytes("UTF-8"))
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return username != null
                    ? new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList())
                    : null;
        }
        return null;
    }
}
