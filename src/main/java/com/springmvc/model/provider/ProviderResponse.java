package com.springmvc.model.provider;

import com.springmvc.model.entity.User;

/**
 * All provider calls made return this
 */
public class ProviderResponse {
    private User user;
    private int tripTime;
    private String message;

    public ProviderResponse(String message) {
        this.message = message;
    }

    public ProviderResponse(User user, String message) {
        this(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTripTime() {
        return tripTime;
    }

    public void setTripTime(int tripTime) {
        this.tripTime = tripTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
