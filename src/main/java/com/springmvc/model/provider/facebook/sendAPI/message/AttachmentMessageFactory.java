package com.springmvc.model.provider.facebook.sendAPI.message;

import com.springmvc.model.provider.facebook.sendAPI.message.attachment.AbstractPayload;
import com.springmvc.model.provider.facebook.shared.Attachment;
import com.springmvc.model.provider.facebook.shared.AttachmentType;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.MultimediaPayload;

public class AttachmentMessageFactory {

    public AttachmentMessage getMessage(AttachmentType type, String attachmentUrl) {
        AbstractPayload payload = new MultimediaPayload(attachmentUrl);
        Attachment attachment = new Attachment(type, payload);

        return new AttachmentMessage(attachment);
    }
}
