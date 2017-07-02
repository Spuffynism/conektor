package com.springmvc.model.provider;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provider's individual dispatcher
 */
public abstract class AbstractProviderDispatcher<T extends AbstractProviderArgument, U extends
        IProviderResponse> {
    private Class<T> argumentType;

    public AbstractProviderDispatcher(Class<T> argumentType) {
        this.argumentType = argumentType;
    }

    public List<U> dispatch(Map<String, String> arguments) throws
            InvalidArgumentException{
        List<T> trelloArguments = new ArrayList<>();
        for (String key : arguments.keySet())
            trelloArguments.add(tryConvert(key, arguments.get(key)));

        return dispatch(trelloArguments);
    }

    private T tryConvert(String action, String value) throws InvalidArgumentException{
        T t;
        try {
            t = argumentType.newInstance();
        } catch (InstantiationException | IllegalAccessException  e) {
            throw new InvalidArgumentException(new String[]{"invalid argument class"});
        }

        t.setAction(action);
        t.setValue(value);

        return t;
    }

    protected abstract List<U> dispatch(List<T> arguments) throws InvalidArgumentException;
}
