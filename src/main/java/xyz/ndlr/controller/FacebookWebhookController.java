package xyz.ndlr.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.exception.InvalidFacebookVerificationToken;
import xyz.ndlr.exception.UnregisteredAccountException;
import xyz.ndlr.model.FacebookMessageConsumer;
import xyz.ndlr.model.dispatching.ErrorDispatcher;
import xyz.ndlr.model.dispatching.MainDispatcher;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.FacebookMessageFacade;
import xyz.ndlr.model.provider.facebook.FacebookService;
import xyz.ndlr.model.provider.facebook.FacebookVerificationToken;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.provider.facebook.webhook.Payload;

import java.util.List;
import java.util.Map;

/**
 * Entry-point for all facebook webhook actions.
 * Receives messages which can be verification requests or relayed user messages.
 */
@RestController
@RequestMapping("/facebook/webhook")
public class FacebookWebhookController {
    private static final Logger logger = Logger.getLogger(FacebookWebhookController.class);

    private final FacebookService facebookService;
    private final ErrorDispatcher errorDispatcher;
    private final MainDispatcher mainDispatcher;
    private final FacebookMessageConsumer facebookMessageConsumer;

    @Autowired
    public FacebookWebhookController(FacebookService facebookService,
                                     ErrorDispatcher errorDispatcher,
                                     MainDispatcher mainDispatcher,
                                     FacebookMessageConsumer facebookMessageConsumer) {
        this.facebookService = facebookService;
        this.errorDispatcher = errorDispatcher;
        this.mainDispatcher = mainDispatcher;
        this.facebookMessageConsumer = facebookMessageConsumer;

        startConsuming();
    }

    /**
     * Starts the message consumer thread
     */
    private void startConsuming() {
        facebookMessageConsumer.startConsuming();
        new Thread(facebookMessageConsumer).start();
    }

    /**
     * Entry point Facebook uses to verify OUR webhook's authenticity by having us return a
     * challenge. We also validate the verify_token that they send to verify THEIR authenticity.
     *
     * @param requestParams the params. facebook sends to use to authentify
     * @return a confirmation response
     */
    @GetMapping
    public ResponseEntity<String> subscribe(@RequestParam Map<String, String> requestParams) {
        FacebookVerificationToken token = new FacebookVerificationToken(requestParams);

        try {
            token.validate();
        } catch (InvalidFacebookVerificationToken e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(token.getChallenge(), HttpStatus.OK);
    }

    /**
     * Entry point for messages sent to our Facebook page.
     *
     * @param payload the message payload sent by facebook
     */
    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody Payload payload) {
        try {
            processPayload(payload);
        } catch (Exception e) {
            logger.error("An error occured during payload processing. Will now " +
                    "attempt to tell recipient.", e);
            errorDispatcher.dispatchIfPossible(payload, e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Validates the received facebook payload before attempting to dispatch and queue the messages.
     *
     * @param payload the message payload sent by facebook
     * @throws UnregisteredAccountException when a user is not registered
     * @throws CannotDispatchException      when an error occurs during dispatching
     * @throws IllegalArgumentException     when some payload info is invalid
     */
    private void processPayload(Payload payload) throws UnregisteredAccountException,
            CannotDispatchException, IllegalArgumentException {
        if (payload == null || !payload.isPage())
            throw new IllegalArgumentException("unsupported payload");

        List<FacebookMessageFacade> messageFacades = FacebookMessageFacade.fromPayload(payload);

        for (FacebookMessageFacade messageFacade : messageFacades)
            dispatchMessageFacade(messageFacade);
    }

    private void dispatchMessageFacade(FacebookMessageFacade messageFacade)
            throws UnregisteredAccountException, CannotDispatchException {
        String senderId = messageFacade.getSender().getId();
        if (!facebookService.userIsRegistered(senderId))
            throw new UnregisteredAccountException("unrecognized facebook account - are you " +
                    "registered?");

        dispatch(senderId, messageFacade.getMessaging());
    }

    /**
     * Creates a dispatcher which will query the appropriate third parties and then send their
     * responses to the facebook user.
     *
     * @param senderId  the user which sent and which will receive the messages
     * @param messaging messaging sent by the user
     * @throws CannotDispatchException when an error occurs during the message dispatching process
     */
    private void dispatch(String senderId, Messaging messaging)
            throws CannotDispatchException {
        User user = facebookService.getUserByPSID(senderId);
        mainDispatcher.dispatchAndQueue(user, messaging);
    }
}
