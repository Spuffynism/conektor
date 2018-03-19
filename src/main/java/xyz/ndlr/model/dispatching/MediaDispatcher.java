package xyz.ndlr.model.dispatching;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ProviderActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

@Component
public class MediaDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private static final Logger logger = Logger.getLogger(MediaDispatcher.class);

    @Autowired
    MediaDispatcher(ProviderActionRepository providerActionRepository,
                    ProviderResponseQueue sharedResponses) {
        super(providerActionRepository, sharedResponses);
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        if (messaging.getMessage().contains(AttachmentType.IMAGE)) {
            /*AbstractProviderDispatcher<Messaging> dispatcher = providerDispatcherFactory
                    .getFromDestinationProvider(SupportedProvider.IMGUR.value());

            Consumer<ProviderResponse> acceptAndQueueResponse = this::queueResponse;

            dispatcher.dispatch(user, messaging)
                    .thenAccept(acceptAndQueueResponse);*/

            // old code from imgurdispatcher
            /*
            Message message = messaging.getMessage();
        return imgurService.upload(message);
             */
        }
    }
}
