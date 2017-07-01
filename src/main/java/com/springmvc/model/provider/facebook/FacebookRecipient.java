package com.springmvc.model.provider.facebook;

public class FacebookRecipient {
    private String id;

    public FacebookRecipient(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
