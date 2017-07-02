package com.springmvc.model.provider.facebook;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;

public class FacebookMessageFacade {
    private FacebookPayload payload;

    private FacebookMessageFacade(FacebookPayload payload) {
        this.payload = payload;
    }

    public static FacebookMessageFacade fromPayload(FacebookPayload payload) throws
            InvalidArgumentException {
        FacebookMessageFacade messageFacade = new FacebookMessageFacade(payload);

        if (messageFacade.getSender() == null || messageFacade.getSender().getId() == null)
            throw new InvalidArgumentException(new String[]{"no sender"});

        if (messageFacade.getMessagings() == null || messageFacade.getMessagings().get(0) == null)
            throw new InvalidArgumentException(new String[]{"no message"});

        return messageFacade;
    }

    public FacebookSender getSender() {
        return payload.getEntry().get(0).getMessaging().get(0).getSender();
    }

    public List<FacebookMessaging> getMessagings() {
        return payload.getEntry().get(0).getMessaging();
    }
}
