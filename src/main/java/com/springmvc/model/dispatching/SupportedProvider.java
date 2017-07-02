package com.springmvc.model.dispatching;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.trello.TrelloDispatcher;
import com.springmvc.model.provider.twitter.TwitterDispatcher;

public enum SupportedProvider {
    TRELLO("trello", new TrelloDispatcher()),
    TWITTER("twitter", new TwitterDispatcher()),
    SSH("ssh", null);

    private String value;
    private AbstractProviderDispatcher providerDispatcher;

    SupportedProvider(String value, AbstractProviderDispatcher providerDispatcher) {
        this.value = value;
        this.providerDispatcher = providerDispatcher;
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
}
