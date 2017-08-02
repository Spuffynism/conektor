package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.entity.User;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainDispatcher implements IMessagingDispatcher {

    private final MediaDispatcher mediaDispatcher;
    private final TextDispatcher textDispatcher;

    @Autowired
    public MainDispatcher(MediaDispatcher mediaDispatcher, TextDispatcher textDispatcher) {
        this.mediaDispatcher = mediaDispatcher;
        this.textDispatcher = textDispatcher;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        if (user == null)
            throw new CannotDispatchException("user cannot be null - a message needs a " +
                    "destination");

        if (messaging.getMessage().containsMedia()) {
            mediaDispatcher.dispatchAndQueue(user, messaging);
        } else {
            textDispatcher.dispatchAndQueue(user, messaging);
        }
    }
}
