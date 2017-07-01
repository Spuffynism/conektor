package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.IProviderResponse;

public class TrelloResponse implements IProviderResponse {
    private int tripTime;
    private boolean isSuccessful;
    private String message;

    public TrelloResponse() {
    }

    public TrelloResponse(int tripTime, boolean isSuccessful, String message) {
        this.tripTime = tripTime;
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public int getTripTime() {
        return tripTime;
    }

    public void setTripTime(int tripTime) {
        this.tripTime = tripTime;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
