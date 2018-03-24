package xyz.ndlr.model.dispatching;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ActionRepository;
import xyz.ndlr.model.provider.ProviderResponse;

@Component
abstract class AbstractSubDispatcher {
    private static final Logger logger = Logger.getLogger(AbstractSubDispatcher.class);

    final ActionRepository actionRepository;
    private final ProviderResponseQueue sharedResponses;

    AbstractSubDispatcher(ActionRepository actionRepository,
                          ProviderResponseQueue sharedResponses) {
        this.actionRepository = actionRepository;
        this.sharedResponses = sharedResponses;
    }

    void queueResponse(ProviderResponse response) {
        try {
            sharedResponses.put(response);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}