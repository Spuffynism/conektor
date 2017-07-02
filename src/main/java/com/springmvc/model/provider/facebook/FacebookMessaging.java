package com.springmvc.model.provider.facebook;

/**
 * Holds info on messages sent to the bot.
 */
public class FacebookMessaging {
    private FacebookSender sender;
    private FacebookRecipient recipient;
    private long timestamp;
    private FacebookMessage message;

    public FacebookMessaging() {
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public FacebookMessage getMessage() {
        return message;
    }

    public void setMessage(FacebookMessage message) {
        this.message = message;
    }
}
