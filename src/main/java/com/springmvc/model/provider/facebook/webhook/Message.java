package com.springmvc.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import com.springmvc.model.provider.facebook.shared.Attachment;
import com.springmvc.model.provider.facebook.shared.AttachmentType;

import java.util.List;
import java.util.stream.Collectors;

public class Message {
    @JsonProperty("mid")
    private String mid;
    @JsonProperty("text")
    private String text;
    @JsonProperty("seq")
    private String seq;
    @JsonProperty("attachments")
    private List<Attachment> attachments;
    @JsonProperty("quick_reply")
    private QuickReply quickReply;

    public Message() {
    }

    public boolean containsImages() {
        return getImageURLs().size() > 0;
    }

    public List<String> getImageURLs() {
        return attachments.stream()
                .filter(a -> a.getType().equals(AttachmentType.IMAGE))
                .filter(a -> a.getPayload() instanceof MultimediaPayload)
                .map(a -> (MultimediaPayload)a.getPayload())
                .map(MultimediaPayload::getUrl)
                .collect(Collectors.toList());

    }

    public String getText() {
        return text;
    }
}
