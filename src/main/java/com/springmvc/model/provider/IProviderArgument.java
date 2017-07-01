package com.springmvc.model.provider;

public interface IProviderArgument<T, U> {
    public T getAction();
    public void setAction(T action);
    public U getValue();
    public void setValue(U value);
}
