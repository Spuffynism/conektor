package xyz.ndlr.infrastructure.dispatching;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.exception.UnregisteredAccountException;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.ProviderResponseError;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.ProviderResponseQueue;
import xyz.ndlr.infrastructure.mapping.ActionRepository;
import xyz.ndlr.infrastructure.provider.facebook.FacebookUserService;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Messaging;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Payload;

import java.util.stream.Stream;

@Component
public class ErrorDispatcher extends AbstractSubDispatcher {
    private static final Logger logger = Logger.getLogger(ErrorDispatcher.class);

    private final FacebookUserService facebookUserService;

    @Autowired
    ErrorDispatcher(ActionRepository actionRepository,
                    ProviderResponseQueue sharedResponses,
                    FacebookUserService facebookUserService) {
        super(actionRepository, sharedResponses);
        this.facebookUserService = facebookUserService;
    }

    @Override
    public Stream<ProviderResponse> onDispatchAndQueue(User user, Messaging messaging) {
        return onDispatchAndQueue(user, messaging.getMessage().getText());
    }

    private Stream<ProviderResponse> onDispatchAndQueue(User user, String message) {
        ProviderResponseError error = new ProviderResponseError(user, message);

        return Stream.of(error);
    }

    public Stream<ProviderResponse> dispatchIfPossible(Payload payload, Exception exception) {
        String senderId = payload.tryGetSenderId();

        if (senderId == null) {
            logger.error("Could not get sender. Error message won't be sent.");
            return null;
        }

        return dispatchExceptionMessage(senderId, exception);
    }

    private Stream<ProviderResponse> dispatchExceptionMessage(String senderId, Exception
            exception) {
        User user = facebookUserService.getUserByPSID(senderId);

        if (isAllowedException(exception)) {
            return onDispatchAndQueue(user, exception.getMessage());
        } else {
            return sendGenericMessage(user);
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

    private Stream<ProviderResponse> sendGenericMessage(User user) {
        String genericErrorMessage = "An unknown error has occured. Please check your message is " +
                "correctly formatted.";
        return onDispatchAndQueue(user, genericErrorMessage);
    }
}