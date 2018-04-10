package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WebviewHeightRatio {
    @JsonProperty("compact")
    COMPACT,
    @JsonProperty("full")
    FULL,
    @JsonProperty("tall")
    TALL;

    private static final WebviewHeightRatio DEFAULT = FULL;
}
