package com.springmvc.controller;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.InvalidFacebookVerificationToken;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.dispatching.Dispatcher;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.*;
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

    @RequestMapping(method = RequestMethod.POST)
    public void receiveMessage(@RequestBody FacebookPayload payload)
            throws UnregisteredAccountException {
        FacebookMessageFacade facebokMessageFacade = new FacebookMessageFacade(payload);
        String senderId = facebokMessageFacade.getSender().getId();

        if (!facebookService.userIsRegistered(senderId))
            throw new UnregisteredAccountException(); //TODO Send obj facebook wants

        List<FacebookMessaging> messagings = facebokMessageFacade.getMessagings();

        try {
            // There should  be one thread per sent message
            tryDispatchAndRespondToUser(senderId, messagings);
        } catch (Exception e) {
            // Tell user we've got a problem
            System.out.println("Error has occured during dispatch");
        }
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
