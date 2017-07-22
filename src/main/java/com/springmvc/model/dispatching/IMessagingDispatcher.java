package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.provider.facebook.webhook.Messaging;

import java.util.List;

public interface IMessagingDispatcher {

    default void dispatch(List<Messaging> messagings) throws CannotDispatchException {
        for (Messaging messaging : messagings)
            dispatch(messaging);
    }

    void dispatch(Messaging messaging) throws CannotDispatchException;
}
