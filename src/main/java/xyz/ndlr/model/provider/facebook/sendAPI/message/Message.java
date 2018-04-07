package xyz.ndlr.model.provider.facebook.sendAPI.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.sendAPI.message.quick_reply.QuickReply;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * message part of payloads sent to facebook
 *
 * @see
 * <a href="https://developers.facebook.com/docs/messenger-platform/reference/send-api/#message"/>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Message {
    /**
     * Optional. Custom string that is delivered as a message echo.
     */
    @JsonProperty("metadata")
    @Max(1000)
    private String metadata;
    /**
     * Optional. Array of quick_reply to be sent with messages
     */
    @JsonProperty("quick_replies")
    private List<QuickReply> quickReplies;

    public Message() {
    }

    public Message(List<QuickReply> quickReplies) {
        this.quickReplies = quickReplies;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public List<QuickReply> getQuickReplies() {
        return quickReplies;
    }

    public void setQuickReplies(List<QuickReply> quickReplies) {
        this.quickReplies = quickReplies;
    }
}
