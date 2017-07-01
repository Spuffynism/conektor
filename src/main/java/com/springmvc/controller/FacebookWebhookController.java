package com.springmvc.controller;

import com.springmvc.exception.InvalidFacebookVerificationToken;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.dispatching.Dispatcher;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.FacebookMessageFacade;
import com.springmvc.model.provider.facebook.FacebookMessaging;
import com.springmvc.model.provider.facebook.FacebookPayload;
import com.springmvc.model.provider.facebook.FacebookVerificationToken;
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
    public ResponseEntity<String> receiveMessage(@RequestBody FacebookPayload payload)
            throws UnregisteredAccountException {
        FacebookMessageFacade facebokMessageFacade = new FacebookMessageFacade(payload);
        String senderId = facebokMessageFacade.getSender().getId();
        if (!facebookService.userIsRegistered(senderId))
            throw new UnregisteredAccountException(); //TODO Send obj facebook wants

        List<FacebookMessaging> messagings = facebokMessageFacade.getMessagings();

        Dispatcher dispatcher = new Dispatcher();
        List<IProviderResponse> responses;
        try {
            // TODO Get response(s) to send back to user
            dispatcher.dispatch(messagings);
            responses = dispatcher.getResponses();
        } catch (Exception e) { //CannotDispatchException
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //TODO Return the objet facebook wants instead of this
        return new ResponseEntity<>(toHumanMessage(responses), HttpStatus.OK);
    }

    private String toHumanMessage(List<IProviderResponse> responses) {
        return "Everything is a-ok!";
    }
}
