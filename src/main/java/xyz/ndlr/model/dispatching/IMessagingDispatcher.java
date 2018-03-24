package xyz.ndlr.model.dispatching;

import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

public interface IMessagingDispatcher {
    void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException;
}
