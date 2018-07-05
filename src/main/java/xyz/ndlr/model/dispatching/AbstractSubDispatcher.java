package xyz.ndlr.model.dispatching;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

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