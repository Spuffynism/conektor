package xyz.ndlr.domain.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.domain.provider.facebook.sendAPI.recipient.Recipient;

/**
 * Payload that is sent to the facebook Send API.
 *
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/send-api/"/>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SendablePayload {
    /**
     * This messaging type might change in the future. But for now, the bot only produces
     * messagings of the DEFAULT type.
     */
    @JsonProperty("messaging_type")
    private MessagingType messagingType = MessagingType.DEFAULT;
    /**
     * Recipient of the payload (usually the user that sent the message to the facebook bot).
     */
    @JsonProperty("recipient")
    private Recipient recipient;
    @JsonProperty("notification_type")
    private NotificationType notificationType = NotificationType.DEFAULT;

    public SendablePayload(Recipient recipient) {
        this.recipient = recipient;
    }

    public MessagingType getMessagingType() {
        return messagingType;
    }

    public void setMessagingType(MessagingType messagingType) {
        this.messagingType = messagingType;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
