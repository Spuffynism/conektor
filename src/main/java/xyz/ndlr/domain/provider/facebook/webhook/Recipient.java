package xyz.ndlr.domain.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipient {
    /**
     * Recipient user ID - the page's id
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
