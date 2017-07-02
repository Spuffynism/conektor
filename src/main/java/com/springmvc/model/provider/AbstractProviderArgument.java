package com.springmvc.model.provider;

public abstract class AbstractProviderArgument<T, U> {
    protected T action;
    protected U value;

    public T getAction() {
        return action;
    }

    void setAction(T action) {
        this.action = action;
    }

    public abstract void setAction(String action);

    public U getValue() {
        return value;
    }

    void setValue(U value) {
        this.value = value;
    }

    public abstract void setValue(String value);
}
