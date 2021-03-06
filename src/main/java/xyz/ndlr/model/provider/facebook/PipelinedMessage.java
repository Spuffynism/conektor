package xyz.ndlr.model.provider.facebook;

import xyz.ndlr.model.parsing.ParsedMessage;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

/**
 * Dispatched down the pipeline to providers
 */
public class PipelinedMessage {
    private Messaging originalMessaging;
    private ParsedMessage parsedMessage;

    public PipelinedMessage(Messaging originalMessaging, ParsedMessage parsedMessage) {
        this.originalMessaging = originalMessaging;
        this.parsedMessage = parsedMessage;
    }

    public Messaging getOriginalMessaging() {
        return originalMessaging;
    }

    public void setOriginalMessaging(Messaging originalMessaging) {
        this.originalMessaging = originalMessaging;
    }

    public ParsedMessage getParsedMessage() {
        return parsedMessage;
    }

    public void setParsedMessage(ParsedMessage parsedMessage) {
        this.parsedMessage = parsedMessage;
    }

    public String getTextWithoutFirstWord() {
        return originalMessaging.getMessage().getTextWithoutFirstWord();
    }
}
