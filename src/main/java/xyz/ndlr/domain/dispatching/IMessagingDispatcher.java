package xyz.ndlr.domain.dispatching;

import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.provider.facebook.webhook.Messaging;

interface IMessagingDispatcher {
    void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException;
}
