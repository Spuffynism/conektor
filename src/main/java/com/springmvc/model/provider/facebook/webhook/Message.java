package com.springmvc.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.facebook.shared.Attachment;

import java.util.List;

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

    public Message(){}

    public String getText() {
        return text;
    }
}
