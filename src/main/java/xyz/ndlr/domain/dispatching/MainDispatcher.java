package xyz.ndlr.domain.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.provider.facebook.webhook.Messaging;

@Component
public class MainDispatcher implements IMessagingDispatcher {

    private final MediaDispatcher mediaDispatcher;
    private final TextDispatcher textDispatcher;

    @Autowired
    public MainDispatcher(MediaDispatcher mediaDispatcher,
                          TextDispatcher textDispatcher) {
        this.mediaDispatcher = mediaDispatcher;
        this.textDispatcher = textDispatcher;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        if (user == null) {
            throw new CannotDispatchException("user cannot be null - a message needs a " +
                    "destination");
        }

        if (messaging.getMessage().containsMedia()) {
            mediaDispatcher.dispatchAndQueue(user, messaging);
        } else {
            textDispatcher.dispatchAndQueue(user, messaging);
        }
    }
}
