package xyz.ndlr.infrastructure.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.DispatchingUserCannotBeNullException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Messaging;

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
    public void dispatchAndQueue(User user, Messaging messaging)
            throws DispatchingUserCannotBeNullException {
        if (user == null) {
            throw new DispatchingUserCannotBeNullException();
        }

        if (messaging.containsMedia()) {
            mediaDispatcher.dispatchAndQueue(user, messaging);
        } else {
            textDispatcher.dispatchAndQueue(user, messaging);
        }
    }
}
