package xyz.ndlr.infrastructure.dispatching;

import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Messaging;

interface IMessagingDispatcher {
    void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException;
}
