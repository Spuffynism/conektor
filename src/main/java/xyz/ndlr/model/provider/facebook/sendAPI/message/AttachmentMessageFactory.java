package xyz.ndlr.model.provider.facebook.sendAPI.message;

import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.AbstractPayload;
import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import xyz.ndlr.model.provider.facebook.shared.Attachment;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;

public class AttachmentMessageFactory {

    public AttachmentMessage getMessage(AttachmentType type, String attachmentUrl) {
        AbstractPayload payload = new MultimediaPayload(attachmentUrl);
        Attachment attachment = new Attachment(type, payload);

        return new AttachmentMessage(attachment);
    }
}
