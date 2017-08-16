package xyz.ndlr.model.provider.facebook.sendAPI.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Message {
    // optional
    @JsonProperty("quick_replies")
    private List<QuickReply> quickReplies;
    // optional
    @JsonProperty("metadata")
    @Max(1000)
    private String metadata;
}
