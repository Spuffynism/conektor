package com.springmvc.controller;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.InvalidFacebookVerificationToken;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.FacebookMessageConsumer;
import com.springmvc.model.dispatching.ErrorDispatcher;
import com.springmvc.model.dispatching.MainDispatcher;
import com.springmvc.model.entity.User;
import com.springmvc.model.provider.facebook.FacebookMessageFacade;
import com.springmvc.model.provider.facebook.FacebookService;
import com.springmvc.model.provider.facebook.FacebookVerificationToken;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.provider.facebook.webhook.Payload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/facebook/webhook")
public class FacebookWebhookController {
    private static final Logger logger = Logger.getLogger(FacebookWebhookController.class);
    private static Executor pool = Executors.newCachedThreadPool();

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

        facebookMessageConsumer.startConsuming();
        new Thread(facebookMessageConsumer).start();
    }

    /**
     * Entry point Facebook uses to verify OUR webhook authenticity by having us return a
     * challenge. We also validate the verify_token that they send to verify THEIR authenticity.
     *
     * @param requestParams the params. facebook sends to use to authentify
     * @return a confirmation response
     */
    @RequestMapping(method = RequestMethod.GET)
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
     * Entry point for messages sent to our Facebook page. Since Facebook wants a 200 OK response
     * ASAP, we process the payload in an other thread and tell Facebook we successfully received
     * their message.
     *
     * @param payload the message payload sent by facebook
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> receiveMessage(@RequestBody Payload payload) {
        Runnable payloadProcessing = () -> {
            try {
                processPayload(payload);
            } catch (Exception e) {
                logger.error("An error occured during payload processing. Will now attempt to " +
                        "tell recipient", e);
                errorDispatcher.dispatchIfPossible(payload, e);
            }
        };

        pool.execute(payloadProcessing);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Does various validation on the received facebook payload before attempting to
     * dispatchAndQueue the
     * messages.
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

        FacebookMessageFacade messageFacade = FacebookMessageFacade.fromPayload(payload);

        String senderId = messageFacade.getSender().getId();
        if (!facebookService.userIsRegistered(senderId))
            throw new UnregisteredAccountException("unrecognized facebook account - are you " +
                    "registered?");

        dispatch(senderId, messageFacade.getMessagings());
    }

    /**
     * Creates a dispatcher which will query the appropriate third parties and then send their
     * responses to the facebook user.
     *
     * @param senderId   the user which sent and which will receive the messages
     * @param messagings messagings sent by the user
     * @throws CannotDispatchException when an error occurs during the message dispatching process
     */
    private void dispatch(String senderId, List<Messaging> messagings)
            throws CannotDispatchException {
        User user = facebookService.getUserByIdentifier(senderId);
        mainDispatcher.dispatchAndQueue(user, messagings);
    }
}
