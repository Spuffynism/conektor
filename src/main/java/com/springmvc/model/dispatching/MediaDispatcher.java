package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.ProviderResponseQueue;
import com.springmvc.model.entity.User;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.shared.AttachmentType;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MediaDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private static final Logger logger = Logger.getLogger(MediaDispatcher.class);

    @Autowired
    MediaDispatcher(ProviderDispatcherFactory providerDispatcherFactory,
                    ProviderResponseQueue sharedResponses) {
        super(providerDispatcherFactory, sharedResponses);
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        if (messaging.getMessage().contains(AttachmentType.IMAGE)) {
            AbstractProviderDispatcher<Messaging> dispatcher = providerDispatcherFactory
                    .getFromDestinationProvider(SupportedProvider.IMGUR);

            Consumer<ProviderResponse> acceptAndQueueResponse = this::queueResponse;

            dispatcher.dispatch(user, messaging)
                    .thenAccept(acceptAndQueueResponse);
        }
    }
}
