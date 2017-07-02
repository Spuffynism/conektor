package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookMessageFacade {
    private FacebookPayload payload;

    public FacebookMessageFacade(FacebookPayload payload) {
        this.payload = payload;
    }

    public FacebookSender getSender() {
        return payload.getEntry().get(0).getMessaging().get(0).getSender();
    }

    public List<FacebookMessaging> getMessagings() {
        return payload.getEntry().get(0).getMessaging();
    }

    public FacebookPayload getPayload() {
        return payload;
    }

    public void setPayload(FacebookPayload payload) {
        this.payload = payload;
    }
}
