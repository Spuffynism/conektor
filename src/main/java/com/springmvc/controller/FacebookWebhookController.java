package com.springmvc.controller;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.CannotSendMessageException;
import com.springmvc.exception.InvalidFacebookVerificationToken;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.dispatching.Dispatcher;
import com.springmvc.model.entity.User;
import com.springmvc.model.provider.facebook.FacebookMessageFacade;
import com.springmvc.model.provider.facebook.FacebookVerificationToken;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.provider.facebook.webhook.Payload;
import com.springmvc.service.provider.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facebook/webhook")
public class FacebookWebhookController {

    private final FacebookService facebookService;
    private final FacebookMessageSender messageSender;

    @Autowired
    public FacebookWebhookController(FacebookService facebookService) {
        this.facebookService = facebookService;
        messageSender = new FacebookMessageSender();
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
        Thread payloadProcessing = new Thread(() -> {
            try {
                processPayload(payload);
            } catch (UnregisteredAccountException | CannotDispatchException |
                    IllegalArgumentException e) {
                System.out.println("An error during payload processing:");
                e.printStackTrace();
                messageSender.sendError(payload, e);
            }
        });

        payloadProcessing.start();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Does various validation on the received facebook payload before attempting to dispatch the
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

        dispatchAndAnswerUser(senderId, messageFacade.getMessagings());
    }

    /**
     * Creates a dispatcher which will query the appropriate third parties and then send their
     * responses to the facebook user.
     *
     * @param senderId   the user which sent and which will receive the messages
     * @param messagings messagings to be parsed
     * @throws CannotDispatchException when an error occured during the message dispatching process
     */
    private void dispatchAndAnswerUser(String senderId, List<Messaging> messagings)
            throws CannotDispatchException {
        User user = facebookService.getUserByIdentifier(senderId);

        Dispatcher dispatcher = new Dispatcher(user, senderId);
        dispatcher.dispatch(messagings);

        try {
            messageSender.send(dispatcher.collectFacebookResponsePayloads());
        } catch (CannotSendMessageException e) {
            e.printStackTrace();
        }
    }
}
