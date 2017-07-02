package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.AbstractProviderArgument;

public class TrelloArgument extends AbstractProviderArgument<String, String> {
    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void setValue(String value) {
        this.action = action;
    }
}
