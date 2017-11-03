package xyz.ndlr.model.dispatching;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.exception.UnregisteredAccountException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponseError;
import xyz.ndlr.model.provider.facebook.FacebookService;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.provider.facebook.webhook.Payload;

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

    public void dispatchIfPossible(Payload payload, Exception exception) {
        String senderId = payload.tryGetSenderId();

        if (senderId == null) {
            logger.error("Could not get sender. Error message won't be sent.");
            return;
        }

        dispatchExceptionMessage(senderId, exception);
    }

    private void dispatchExceptionMessage(String senderId, Exception exception) {
        User user = facebookService.getUserByIdentifier(senderId);

        if (isAllowedException(exception)) {
            dispatchAndQueue(user, exception.getMessage());
        } else {
            sendGenericMessage(user);
        }
    }

    /**
     * Indicates if an exception is allowed to be shown to the user.
     *
     * @param exception
     * @return if the exception is allowed to be shown to the user
     */
    private static boolean isAllowedException(Exception exception) {
        return exception instanceof CannotDispatchException ||
                exception instanceof UnregisteredAccountException;
    }

    private void sendGenericMessage(User user) {
        String genericErrorMessage = "An unknown error has occured. Please check your message is " +
                "correctly formatted.";
        dispatchAndQueue(user, genericErrorMessage);
    }
}
