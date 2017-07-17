package com.springmvc.model.provider.facebook.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.AbstractPayload;

/**
 * TODO Make it possible to upload files to Facebook
 * https://developers.facebook.com/docs/messenger-platform/send-api-reference/image-attachment
 */
public class Attachment {
    // required
    @JsonProperty("type")
    private AttachmentType type;
    // null if AttachmentType is FALLBACK
    @JsonProperty("payload")
    private AbstractPayload payload;
    @JsonProperty("title")
    private String title;
    @JsonProperty("URL")
    private String URL;

    public Attachment() {
    }

    public Attachment(AttachmentType type, AbstractPayload payload) {
        this.type = type;
        this.payload = payload;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public AbstractPayload getPayload() {
        return payload;
    }

    public void setPayload(AbstractPayload payload) {
        this.payload = payload;
    }
}
