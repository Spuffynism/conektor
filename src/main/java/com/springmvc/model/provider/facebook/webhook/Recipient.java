package com.springmvc.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipient {
    /**
     * Recipient user ID - the page's id
     */
    @JsonProperty("id")
    private String id;
}
