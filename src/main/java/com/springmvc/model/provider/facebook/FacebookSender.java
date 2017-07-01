package com.springmvc.model.provider.facebook;

public class FacebookSender {
    private String id;

    public FacebookSender() {
    }

    public FacebookSender(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
