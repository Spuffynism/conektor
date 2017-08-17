package xyz.ndlr.model.provider.facebook;

import xyz.ndlr.model.dispatching.IMessage;
import xyz.ndlr.model.parsing.ParsedMessage;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

/**
 * Message contents that are send down the dispatching pipeline to end up in individual providers'
 * hands
 */
// TODO Rename to: MetadataPacket, DataPacket, PacketMessage, SelfContainedPacketMessage,
// TransferPacket, TransferMessage, Transfer?
public class PipelinedMessage implements IMessage {
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
}