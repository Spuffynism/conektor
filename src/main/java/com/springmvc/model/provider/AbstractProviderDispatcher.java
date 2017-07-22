package com.springmvc.model.provider;

import com.springmvc.model.provider.facebook.PipelinedMessage;

import java.util.List;
import java.util.Map;

/**
 * Provider's individual dispatcher
 */
public abstract class AbstractProviderDispatcher<T> {
    public abstract List<ProviderResponse> dispatch(T message)
            throws IllegalArgumentException;

    protected static <E extends Enum<E>> E getFirstAction(Map<String, String> arguments,
                                                          Class<E> action) {
        Map.Entry<String, String> firstEntry = arguments.entrySet().iterator().next();
        E firstAction;

        try {
            firstAction = E.valueOf(action, firstEntry.getKey().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("unknown action");
        }

        return firstAction;
    }
}
