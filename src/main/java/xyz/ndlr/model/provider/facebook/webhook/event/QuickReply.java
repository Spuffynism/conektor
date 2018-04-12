package xyz.ndlr.model.provider.facebook.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

class QuickReply {
    @JsonProperty("payload")
    private String payload;
}
