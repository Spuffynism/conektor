package com.springmvc.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sender {
    /**
     * Sender user ID
     */
    @JsonProperty("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
