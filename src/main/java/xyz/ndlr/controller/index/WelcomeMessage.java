package xyz.ndlr.controller.index;

import java.util.Date;

public class WelcomeMessage {
    private long timestamp;
    private String message;

    WelcomeMessage(String message) {
        this.timestamp = new Date().getTime();
        this.message = message;
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
}
