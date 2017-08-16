package xyz.ndlr.model.provider.facebook.sendAPI.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.shared.Attachment;

public class AttachmentMessage extends Message {
    // text or attachment
    @JsonProperty("attachment")
    private Attachment attachment;

    public AttachmentMessage(Attachment attachment) {
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
