package xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.attachment;

import xyz.ndlr.infrastructure.provider.facebook.shared.Attachment;
import xyz.ndlr.infrastructure.provider.facebook.shared.AttachmentType;

public class AttachmentMessageFactory {

    public AttachmentMessage getMessage(AttachmentType type, String attachmentUrl) {
        AbstractPayload payload = new MultimediaPayload(attachmentUrl);
        Attachment attachment = new Attachment(type, payload);

        return new AttachmentMessage(attachment);
    }
}
