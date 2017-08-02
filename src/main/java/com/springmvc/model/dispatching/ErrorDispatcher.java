package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.ProviderResponseQueue;
import com.springmvc.model.entity.User;
import com.springmvc.model.provider.ProviderResponseError;
import com.springmvc.model.provider.facebook.FacebookService;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.provider.facebook.webhook.Payload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private static final Logger logger = Logger.getLogger(ErrorDispatcher.class);

    private final FacebookService facebookService;

    @Autowired
    ErrorDispatcher(ProviderDispatcherFactory providerDispatcherFactory,
                    ProviderResponseQueue sharedResponses,
                    FacebookService facebookService) {
        super(providerDispatcherFactory, sharedResponses);
        this.facebookService = facebookService;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        dispatchAndQueue(user, messaging.getMessage().getText());
    }

    private void dispatchAndQueue(User user, String message) {
        ProviderResponseError error = new ProviderResponseError(user, message);

        queueResponse(error);
    }

    public void dispatchIfPossible(Payload payload, Throwable throwable) {
        String recipientId = payload.tryGetRecipientId();

        if (recipientId == null) {
            logger.error("Could not get recipient. Error message won't be sent.");
            return;
        }

        dispatchThrowableMessage(recipientId, throwable);
    }

    private void dispatchThrowableMessage(String recipientId, Throwable throwable) {
        User user = facebookService.getUserByIdentifier(recipientId);

        if (isAllowedThrowable(throwable)) {
            dispatchAndQueue(user, throwable.getMessage());
        } else {
            sendGenericMessage(user);
        }
    }

    private static boolean isAllowedThrowable(Throwable throwable) {
        return throwable instanceof CannotDispatchException ||
                throwable instanceof UnregisteredAccountException;
    }

    private void sendGenericMessage(User user) {
        String genericErrorMessage = "An unknown error has occured. Please check your message is " +
                "correctly formatted.";
        dispatchAndQueue(user, genericErrorMessage);
    }
}
