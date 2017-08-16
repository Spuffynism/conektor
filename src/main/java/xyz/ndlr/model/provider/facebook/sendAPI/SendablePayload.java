package xyz.ndlr.model.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.sendAPI.message.Message;
import xyz.ndlr.model.provider.facebook.sendAPI.recipient.Recipient;

/**
 * TODO: Make MessagePayload and SenderActionPayload
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendablePayload {
    // Required
    @JsonProperty("recipient")
    private Recipient recipient;
    // message OR senderAction
    @JsonProperty("message")
    private Message message;
    // message OR senderAction
    @JsonProperty("sender_action")
    private SenderAction senderAction;
    // Optional
    @JsonProperty("notification_type")
    private NotificationType notificationType;

    public SendablePayload(Recipient recipient, Message message) {
        this.recipient = recipient;
        this.message = message;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public SenderAction getSenderAction() {
        return senderAction;
    }

    public void setSenderAction(SenderAction senderAction) {
        this.senderAction = senderAction;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
