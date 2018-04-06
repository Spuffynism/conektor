package xyz.ndlr.model.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessagingType {
    @JsonProperty("response")
    RESPONSE,
    @JsonProperty("update")
    UPDATE,
    @JsonProperty("message_tag")
    MESSAGE_TAG
}
