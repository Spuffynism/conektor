package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.list;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TopElementStyle {
    /**
     * default
     */
    @JsonProperty("large")
    LARGE,
    @JsonProperty("compact")
    COMPACT;
}
