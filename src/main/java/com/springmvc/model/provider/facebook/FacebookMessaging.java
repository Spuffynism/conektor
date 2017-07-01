package com.springmvc.model.provider.facebook;

public class FacebookMessaging {
    private FacebookSender sender;
    private FacebookRecipient recipient;
    private String message;

    public FacebookMessaging() {
    }

    public FacebookMessaging(FacebookSender sender, FacebookRecipient recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public FacebookSender getSender() {
        return sender;
    }

    public void setSender(FacebookSender sender) {
        this.sender = sender;
    }

    public FacebookRecipient getRecipient() {
        return recipient;
    }

    public void setRecipient(FacebookRecipient recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
