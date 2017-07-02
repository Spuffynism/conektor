package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookMessageFacade {
    private FacebookPayload payload;

    private FacebookMessageFacade(FacebookPayload payload) {
        this.payload = payload;
    }

    public static FacebookMessageFacade fromPayload(FacebookPayload payload) throws
            IllegalArgumentException {
        FacebookMessageFacade messageFacade = new FacebookMessageFacade(payload);

        if (messageFacade.getSender() == null || messageFacade.getSender().getId() == null)
            throw new IllegalArgumentException("no sender");

        if (messageFacade.getMessagings() == null || messageFacade.getMessagings().get(0) == null)
            throw new IllegalArgumentException("no message");

        return messageFacade;
    }

    public FacebookSender getSender() {
        return payload.getEntry().get(0).getMessaging().get(0).getSender();
    }

    public List<FacebookMessaging> getMessagings() {
        return payload.getEntry().get(0).getMessaging();
    }
}
