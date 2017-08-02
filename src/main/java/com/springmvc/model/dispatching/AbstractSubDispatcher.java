package com.springmvc.model.dispatching;

import com.springmvc.model.ProviderResponseQueue;
import com.springmvc.model.provider.ProviderResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
abstract class AbstractSubDispatcher {
    private static final Logger logger = Logger.getLogger(AbstractSubDispatcher.class);

    final ProviderDispatcherFactory providerDispatcherFactory;
    private final BlockingQueue<ProviderResponse> sharedResponses;

    AbstractSubDispatcher(ProviderDispatcherFactory providerDispatcherFactory,
                          ProviderResponseQueue sharedResponses) {
        this.providerDispatcherFactory = providerDispatcherFactory;
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