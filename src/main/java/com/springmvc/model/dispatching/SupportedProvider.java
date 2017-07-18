package com.springmvc.model.dispatching;

public enum SupportedProvider {
    FACEBOOK("facebook", 1),
    IMGUR("imgur", 4),
    TRELLO("trello", 2),
    SSH("ssh", 3);

    private String value;
    private int providerId;

    SupportedProvider(String value, int providerId) {
        this.value = value;
        this.providerId = providerId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
