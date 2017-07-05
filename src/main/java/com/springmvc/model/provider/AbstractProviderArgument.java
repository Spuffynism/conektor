package com.springmvc.model.provider;

public abstract class AbstractProviderArgument {
    protected String action;
    protected String value;

    public String getAction() {
        return action;
    }

    void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }
}
