package xyz.ndlr.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object is defined as a complete payload from
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/message
 * <p>
 * TODO Create ReceivedMessaging & PostbackMessaging AND make abstract
 */
public class Messaging {
    @JsonProperty("sender")
    private Sender sender;
    @JsonProperty("recipient")
    private Recipient recipient;
    @JsonProperty("timestamp")
    private long timestamp;
    // message OR postback
    @JsonProperty("message")
    private Message message;
    // message OR postback
    @JsonProperty("postback")
    private Postback postback;

    public Messaging() {
    }

    String tryGetSenderId() {
        String senderId = null;

        if (sender != null)
            senderId = sender.getId();

        return senderId;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
