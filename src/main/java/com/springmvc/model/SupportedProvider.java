package com.springmvc.model;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.trello.TrelloDispatcher;
import com.springmvc.model.provider.twitter.TwitterDispatcher;

public enum SupportedProvider {
    FACEBOOK("facebook", null, 1),
    TRELLO("trello", new TrelloDispatcher(), 2),
    TWITTER("twitter", new TwitterDispatcher(), 3),
    SSH("ssh", null, -1);

    private String value;
    private AbstractProviderDispatcher providerDispatcher;
    private int providerId;

    SupportedProvider(String value, AbstractProviderDispatcher providerDispatcher, int providerId) {
        this.value = value;
        this.providerDispatcher = providerDispatcher;
        this.providerId = providerId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AbstractProviderDispatcher getProviderDispatcher() {
        return providerDispatcher;
    }

    public void setProviderDispatcher(AbstractProviderDispatcher providerDispatcher) {
        this.providerDispatcher = providerDispatcher;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
