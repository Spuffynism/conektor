package com.springmvc.controller.index;

import java.util.Date;

public class WelcomeMessage {
    private long timestamp;
    private String message;
    private boolean authenticated;

    public WelcomeMessage(String message) {
        this.timestamp = new Date().getTime();
        setMessage(message);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
