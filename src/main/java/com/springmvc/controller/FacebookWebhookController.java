package com.springmvc.controller;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.InvalidFacebookVerificationToken;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.dispatching.Dispatcher;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.*;
import com.springmvc.service.provider.FacebookMessageSender;
import com.springmvc.service.provider.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/facebook/webhook")
public class FacebookWebhookController {

    private final FacebookService facebookService;

    // Move to mapper class
    private Function<IProviderResponse, FacebookResponsePayload> providerResponseToHumanMessage
            = providerResponse -> {
        FacebookResponsePayload responseForUser = new FacebookResponsePayload();
        responseForUser.setMessage(providerResponse.getMessage());

        return responseForUser;
    };

    @Autowired
    public FacebookWebhookController(FacebookService facebookService) {
        this.facebookService = facebookService;
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
     * @param payload payload send by facebook
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> receiveMessage(@RequestBody FacebookPayload payload) {
        new Thread(() -> {
            try {
                processMessage(payload);
            } catch (Exception e) {
                new FacebookMessageSender().sendError(payload, e);
            }
        }).start();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void processMessage(FacebookPayload payload) throws UnregisteredAccountException,
            CannotDispatchException {
        FacebookMessageFacade facebokMessageFacade = new FacebookMessageFacade(payload);
        String senderId = facebokMessageFacade.getSender().getId();

        if (!facebookService.userIsRegistered(senderId))
            throw new UnregisteredAccountException();

        List<FacebookMessaging> messagings = facebokMessageFacade.getMessagings();

        tryDispatchAndRespondToUser(senderId, messagings);
    }

    private void tryDispatchAndRespondToUser(String senderId, List<FacebookMessaging> messagings)
            throws CannotDispatchException {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setFacebookSenderId(senderId);

        List<IProviderResponse> providerResponses;
        try {
            dispatcher.dispatch(messagings);
            providerResponses = dispatcher.getResponses();

        } catch (Exception e) { //CannotDispatchException
            throw new CannotDispatchException();
        }

        // Move to mapper class
        for (IProviderResponse r : providerResponses) {
            FacebookResponsePayload responseForUser = providerResponseToHumanMessage.apply(r);
            responseForUser.setRecipient(senderId);
        }

        // Send back all messages to user!!!!!
    }

    public void sendMessage(FacebookResponsePayload message) {

    }

    private String toHumanMessage(List<IProviderResponse> responses) {
        return "Everything is a-ok!";
    }
}