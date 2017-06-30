package com.springmvc.controller.receiver;

import com.springmvc.exception.InvalidFacebookVerificationToken;
import com.springmvc.model.facebook.FacebookReceivedMessage;
import com.springmvc.model.facebook.FacebookVerificationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/facebook/webhook")
public class FacebookMessageReceiverController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> verifyRequest(@RequestParam Map<String, String> requestParams) {
        FacebookVerificationToken token = new FacebookVerificationToken(requestParams);

        try {
            token.validate();
        } catch (InvalidFacebookVerificationToken e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(token.getChallenge(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> receiveMessage(@RequestBody FacebookReceivedMessage message) {

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
