package xyz.ndlr.domain.provider.facebook.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuickReply {
    @JsonProperty("payload")
    private String payload;

    public String getPayload() {
        return payload;
    }
}
