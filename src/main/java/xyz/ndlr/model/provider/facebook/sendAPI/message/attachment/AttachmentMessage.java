package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.sendAPI.message.Message;
import xyz.ndlr.model.provider.facebook.sendAPI.message.quick_reply.QuickReply;
import xyz.ndlr.model.provider.facebook.shared.Attachment;

import java.util.List;

public class AttachmentMessage extends Message {
    /**
     * Previews the URL. Used to send messages with media or Structured Messages.
     */
    @JsonProperty("attachment")
    private Attachment attachment;

    public AttachmentMessage(Attachment attachment) {
        this.attachment = attachment;
    }

    public AttachmentMessage(Attachment attachment, List<QuickReply> quickReplies) {
        super(quickReplies);
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
