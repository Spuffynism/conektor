package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.entity.User;

import java.util.List;

public interface IMessagingDispatcher {

    default void dispatchAndQueue(User user, List<Messaging> messagings) throws CannotDispatchException {
        for (Messaging messaging : messagings)
            dispatchAndQueue(user, messaging);
    }

    void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException;
}
