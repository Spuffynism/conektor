package xyz.ndlr.domain.dispatching;

import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.provider.facebook.webhook.Messaging;
import xyz.ndlr.domain.user.User;

interface IMessagingDispatcher {
    void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException;
}
