package xyz.ndlr.model.dispatching;

import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.entity.User;

import java.util.List;

public interface IMessagingDispatcher {

    default void dispatchAndQueue(User user, List<Messaging> messagings) throws CannotDispatchException {
        for (Messaging messaging : messagings)
            dispatchAndQueue(user, messaging);
    }

    void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException;
}
