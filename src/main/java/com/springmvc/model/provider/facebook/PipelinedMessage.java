package com.springmvc.model.provider.facebook;

import com.springmvc.model.parsing.ParsedMessage;
import com.springmvc.model.provider.facebook.webhook.Messaging;

import java.util.Map;

/**
 * Message contents that are send down the dispatching pipeline to end up in individual providers'
 * hands
 */
public class PipelinedMessage {
    private Messaging messaging;
    private ParsedMessage parsedMessage;

    public PipelinedMessage(Messaging messaging, ParsedMessage parsedMessage) {
        this.messaging = messaging;
        this.parsedMessage = parsedMessage;
    }

    public Messaging getMessaging() {
        return messaging;
    }

    public void setMessaging(Messaging messaging) {
        this.messaging = messaging;
    }

    public ParsedMessage getParsedMessage() {
        return parsedMessage;
    }

    public void setParsedMessage(ParsedMessage parsedMessage) {
        this.parsedMessage = parsedMessage;
    }
}
