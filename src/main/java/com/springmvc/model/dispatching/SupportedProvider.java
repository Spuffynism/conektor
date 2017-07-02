package com.springmvc.model.dispatching;

public enum SupportedProvider {
    TRELLO("trello"),
    TWITTER("twitter"),
    SSH("ssh");

    private String value;

    SupportedProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
