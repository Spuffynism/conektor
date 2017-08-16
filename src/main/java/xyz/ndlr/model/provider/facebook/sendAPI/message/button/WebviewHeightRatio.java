package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WebviewHeightRatio {
    @JsonProperty("compact")
    COMPACT,
    /**
     * Default value
     */
    @JsonProperty("full")
    FULL,
    @JsonProperty("tall")
    TALL;
}
