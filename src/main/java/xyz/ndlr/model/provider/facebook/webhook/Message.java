package xyz.ndlr.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import xyz.ndlr.model.provider.facebook.shared.Attachment;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;

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

    public Message() {
    }

    public boolean containsMedia() {
        return attachments != null && attachments.stream()
                .anyMatch(x -> x.getType().isMultimedia());
    }

    public boolean contains(AttachmentType attachmentType) {
        return attachments.stream()
                .anyMatch(x -> x.getType().equals(attachmentType));
    }

    public List<String> getAttachmentURLs(AttachmentType attachmentType) {
        return attachments.stream()
                .filter(a -> a.getType().equals(attachmentType))
                .map(a -> (MultimediaPayload) a.getPayload())
                .map(MultimediaPayload::getUrl)
                .collect(Collectors.toList());

    }

    public String getTextWithoutFirstWord() {
        return text.split(" ", 2)[1];
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
