package xyz.ndlr.infrastructure.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.recipient.Recipient;

public class SenderActionSendablePayload extends SendablePayload {
    @JsonProperty("sender_action")
    private SenderAction senderAction;

    public SenderActionSendablePayload(Recipient recipient, SenderAction senderAction) {
        super(recipient);
        this.senderAction = senderAction;
    }

    public SenderAction getSenderAction() {
        return senderAction;
    }

    public void setSenderAction(SenderAction senderAction) {
        this.senderAction = senderAction;
    }
}
