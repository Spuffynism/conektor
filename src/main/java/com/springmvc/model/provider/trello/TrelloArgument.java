package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.IProviderArgument;

public class TrelloArgument implements IProviderArgument<String, String> {
    private String action;
    private String value;

    public TrelloArgument(String action) {
        this.action = action;
    }

    public TrelloArgument(String action, String value) {
        this.action = action;
        this.value = value;
    }

    ///<editor-fold> Basic getters and setters

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    ///</editor-fold>
}
