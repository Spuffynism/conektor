package com.springmvc.model.provider;

public interface IProviderArgument<T, U> {
    T getAction();
    void setAction(T action);
    U getValue();
    void setValue(U value);
}
