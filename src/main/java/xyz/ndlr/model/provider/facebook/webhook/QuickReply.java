package xyz.ndlr.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

class QuickReply {
    @JsonProperty("payload")
    private String payload;
}
