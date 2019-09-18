package xyz.ndlr.infrastructure.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.Message;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.recipient.Recipient;

public class MessageSendablePayload extends SendablePayload {
    @JsonProperty("message")
    private Message message;

    public MessageSendablePayload(Recipient recipient, Message message) {
        super(recipient);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
