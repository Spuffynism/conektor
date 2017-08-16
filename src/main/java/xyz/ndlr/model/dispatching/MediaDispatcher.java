package xyz.ndlr.model.dispatching;

import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.AbstractProviderDispatcher;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
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
