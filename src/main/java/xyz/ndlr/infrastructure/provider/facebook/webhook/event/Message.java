package xyz.ndlr.infrastructure.provider.facebook.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import xyz.ndlr.infrastructure.provider.facebook.shared.Attachment;
import xyz.ndlr.infrastructure.provider.facebook.shared.AttachmentType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messages
 */
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

    public String getContent() {
        return this.isQuickReply()
                ? this.quickReply.getPayload()
                : this.text;
    }

    private boolean isQuickReply() {
        return this.quickReply != null;
    }

    public String getText() {
        return text;
    }

    public QuickReply getQuickReply() {
        return quickReply;
    }

    public String getTextWithoutFirstWord() {
        return text.split(" ", 2)[1];
    }
}
