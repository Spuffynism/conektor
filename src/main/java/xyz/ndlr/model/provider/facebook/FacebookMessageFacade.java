package xyz.ndlr.model.provider.facebook;

import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.provider.facebook.webhook.Payload;
import xyz.ndlr.model.provider.facebook.webhook.Sender;

import java.util.List;

public class FacebookMessageFacade {
    private Payload payload;

    private FacebookMessageFacade(Payload payload) {
        this.payload = payload;
    }

    public static FacebookMessageFacade fromPayload(Payload payload) throws
            IllegalArgumentException {
        FacebookMessageFacade messageFacade = new FacebookMessageFacade(payload);

        if (messageFacade.getSender() == null || messageFacade.getSender().getId() == null)
            throw new IllegalArgumentException("no sender");

        if (messageFacade.getMessagings() == null || messageFacade.getMessagings().get(0) == null)
            throw new IllegalArgumentException("no message");

        return messageFacade;
    }

    public Sender getSender() {
        return payload.getEntry().get(0).getMessaging().get(0).getSender();
    }

    public List<Messaging> getMessagings() {
        return payload.getEntry().get(0).getMessaging();
    }
}
