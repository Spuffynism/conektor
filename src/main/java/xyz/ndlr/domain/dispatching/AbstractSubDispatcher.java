package xyz.ndlr.domain.dispatching;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.ProviderResponseQueue;
import xyz.ndlr.domain.dispatching.mapping.ActionRepository;
import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.facebook.webhook.Messaging;
import xyz.ndlr.domain.user.User;

import java.util.stream.Stream;

@Component
abstract class AbstractSubDispatcher implements IMessagingDispatcher {
    private static final Logger logger = Logger.getLogger(AbstractSubDispatcher.class);

    final ActionRepository actionRepository;
    private final ProviderResponseQueue sharedResponses;

    AbstractSubDispatcher(ActionRepository actionRepository,
                          ProviderResponseQueue sharedResponses) {
        this.actionRepository = actionRepository;
        this.sharedResponses = sharedResponses;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) {
        try {
            Stream<ProviderResponse> responses = onDispatchAndQueue(user, messaging);
            responses.forEach(response -> {
                try {
                    sharedResponses.put(response);
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            });
        } catch (CannotDispatchException e) {
            logger.error(e);
        }
    }

    abstract protected Stream<ProviderResponse> onDispatchAndQueue(User user, Messaging messaging)
            throws CannotDispatchException;
}