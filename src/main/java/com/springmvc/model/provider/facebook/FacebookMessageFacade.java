package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookMessageFacade {
    private FacebookPayload payload;

    public FacebookMessageFacade() {
    }

    public FacebookMessageFacade(FacebookPayload payload) {
        this.payload = payload;
    }

    public FacebookSender getSender() {
        return payload.getEntries().get(0).getMessagings().get(0).getSender();
    }

    public List<FacebookMessaging> getMessagings() {
        return payload.getEntries().get(0).getMessagings();
    }

    public FacebookPayload getPayload() {
        return payload;
    }

    public void setPayload(FacebookPayload payload) {
        this.payload = payload;
    }
}
