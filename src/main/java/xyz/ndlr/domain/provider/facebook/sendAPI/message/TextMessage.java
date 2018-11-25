package xyz.ndlr.domain.provider.facebook.sendAPI.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.domain.provider.facebook.sendAPI.message.quick_reply.QuickReply;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TextMessage extends Message {
    /**
     * Previews will not be shown for the URLs in this field. Use
     * {@link xyz.ndlr.domain.provider.facebook.sendAPI.message.attachment.AttachmentMessage}
     * instead. Must be UTF-8.
     */
    @JsonProperty("text")
    @Max(2000)
    @NotNull
    private String text;

    public TextMessage(String text) {
        this.text = text;
    }

    public TextMessage(String text, List<QuickReply> quickReplies) {
        super(quickReplies);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
