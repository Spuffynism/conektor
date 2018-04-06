package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This enum is not complete. It is missing all the airline template types.
 */
public enum TemplateType {
    @JsonProperty("button")
    BUTTON,
    @JsonProperty("generic")
    GENERIC,
    @JsonProperty("list")
    LIST,
    @JsonProperty("media")
    MEDIA,
    @JsonProperty("open_graph")
    OPEN_GRAPH,
    @JsonProperty("receipt")
    RECEIPT;
}
