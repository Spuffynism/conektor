package com.springmvc.security.auth.jwt;

public class JWTToken {
    private String token;

    public JWTToken(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
