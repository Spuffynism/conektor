package xyz.ndlr.model.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

@Component
public class MainDispatcher implements IMessagingDispatcher {

    private final MediaDispatcher mediaDispatcher;
    private final TextDispatcher textDispatcher;
    private final QuickReplyDispatcher quickReplyDispatcher;

    @Autowired
    public MainDispatcher(MediaDispatcher mediaDispatcher,
                          TextDispatcher textDispatcher,
                          QuickReplyDispatcher quickReplyDispatcher) {
        this.mediaDispatcher = mediaDispatcher;
        this.textDispatcher = textDispatcher;
        this.quickReplyDispatcher = quickReplyDispatcher;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        if (user == null) {
            throw new CannotDispatchException("user cannot be null - a message needs a " +
                    "destination");
        }

        if (messaging.getMessage().isQuickReply()) {
            quickReplyDispatcher.dispatchAndQueue(user, messaging);
        } else if (messaging.getMessage().containsMedia()) {
            mediaDispatcher.dispatchAndQueue(user, messaging);
        } else {
            textDispatcher.dispatchAndQueue(user, messaging);
        }
    }
}
