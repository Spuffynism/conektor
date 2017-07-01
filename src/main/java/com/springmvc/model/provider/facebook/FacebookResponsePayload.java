package com.springmvc.model.provider.facebook;

public class FacebookResponsePayload {
    private FacebookRecipient recipient;
    private FacebookMessage message;

    public FacebookResponsePayload() {
    }

    public FacebookResponsePayload(String recipientId, String message) {
        this.recipient = new FacebookRecipient(recipientId);
        this.message = new FacebookMessage(message);
    }

    public FacebookRecipient getRecipient() {
        return recipient;
    }

    public void setRecipient(FacebookRecipient recipient) {
        this.recipient = recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = new FacebookRecipient(recipient);
    }

    public FacebookMessage getMessage() {
        return message;
    }

    public void setMessage(FacebookMessage message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = new FacebookMessage(message);
    }
}
