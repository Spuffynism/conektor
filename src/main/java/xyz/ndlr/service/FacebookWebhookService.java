package xyz.ndlr.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.exception.UnregisteredAccountException;
import xyz.ndlr.domain.exception.UnsupportedPayloadException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.dispatching.ErrorDispatcher;
import xyz.ndlr.infrastructure.dispatching.MainDispatcher;
import xyz.ndlr.infrastructure.provider.facebook.FacebookMessageFacade;
import xyz.ndlr.infrastructure.provider.facebook.FacebookUserService;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Messaging;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Payload;

import java.util.List;

@Service
public class FacebookWebhookService {
    private static final Logger logger = Logger.getLogger(FacebookWebhookService.class);

    private final FacebookUserService facebookRepository;
    private final ErrorDispatcher errorDispatcher;
    private final MainDispatcher mainDispatcher;

    FacebookWebhookService(FacebookUserService facebookRepository, ErrorDispatcher errorDispatcher,
                           MainDispatcher mainDispatcher) {
        this.facebookRepository = facebookRepository;
        this.errorDispatcher = errorDispatcher;
        this.mainDispatcher = mainDispatcher;
    }

    /**
     * Validates the received facebook payload before attempting to dispatch and queue the messages.
     *
     * @param payload the message payload sent by facebook
     * @throws IllegalArgumentException     when some payload info is invalid
     */
    public void processPayload(Payload payload) {
        try {
            if (payload == null || !payload.isPage())
                throw new UnsupportedPayloadException();

            List<FacebookMessageFacade> messageFacades = FacebookMessageFacade.fromPayload(payload);

            for (FacebookMessageFacade messageFacade : messageFacades)
                dispatchMessageFacade(messageFacade);
        } catch (Exception e) {
            logger.error("An error occured during payload processing. Will now " +
                    "attempt to tell recipient.", e);
            errorDispatcher.dispatchIfPossible(payload, e);
        }
    }

    private void dispatchMessageFacade(FacebookMessageFacade messageFacade)
            throws UnregisteredAccountException, CannotDispatchException {
        String senderId = messageFacade.getSender().getId();

        if (!facebookRepository.userIsRegistered(senderId))
            throw new UnregisteredAccountException();

        dispatch(senderId, messageFacade.getMessaging());
    }

    /**
     * Creates a dispatcher which will query the appropriate third parties and then send their
     * responses to the facebook user.
     *
     * @param senderId  the user which sent and which will receive the messages
     * @param messaging messaging sent by the user
     * @throws CannotDispatchException when an error occurs during the message dispatching upload
     */
    private void dispatch(String senderId, Messaging messaging)
            throws CannotDispatchException {
        User user = facebookRepository.getUserByPSID(senderId);
        mainDispatcher.dispatchAndQueue(user, messaging);
    }
}
