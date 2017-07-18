package com.springmvc.model.provider;

/**
 * All provider calls made return this
 */
public class ProviderResponse {
    private int tripTime;
    private String message;

    public ProviderResponse(String message) {
        this.message = message;
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
