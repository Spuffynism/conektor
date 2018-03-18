package xyz.ndlr.model.dispatching;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ProviderActionRepository;
import xyz.ndlr.model.provider.ProviderResponse;

@Component
abstract class AbstractSubDispatcher {
    private static final Logger logger = Logger.getLogger(AbstractSubDispatcher.class);

    final ProviderActionRepository providerActionRepository;
    private final ProviderResponseQueue sharedResponses;

    AbstractSubDispatcher(ProviderActionRepository providerActionRepository,
                          ProviderResponseQueue sharedResponses) {
        this.providerActionRepository = providerActionRepository;
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