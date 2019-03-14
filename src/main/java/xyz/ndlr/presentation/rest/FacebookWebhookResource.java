package xyz.ndlr.presentation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ndlr.domain.FacebookMessageConsumer;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;
import xyz.ndlr.domain.provider.facebook.FacebookVerificationToken;
import xyz.ndlr.domain.provider.facebook.webhook.Payload;
import xyz.ndlr.service.FacebookWebhookService;

import java.util.Map;

/**
 * Entry-point for all facebook webhook actions.
 * Receives messages which can be verification requests or relayed user messages.
 */
@RestController
@RequestMapping("/facebook/webhook")
public class FacebookWebhookResource {

    private final FacebookWebhookService facebookWebhookService;

    @Autowired
    public FacebookWebhookResource(FacebookMessageConsumer facebookMessageConsumer,
                                   FacebookWebhookService facebookWebhookService) {
        this.facebookWebhookService = facebookWebhookService;

        // TODO(nich): Handle this thread better
        facebookMessageConsumer.startConsuming();
    }

    /**
     * Entry point Facebook uses to verify OUR webhook's authenticity by having us return a
     * challenge. We also validate the verify_token that they send to verify THEIR authenticity.
     *
     * @param requestParams the params. facebook sends to use to authentify
     * @return a confirmation response
     */
    @GetMapping
    public ResponseEntity<String> subscribe(@RequestParam Map<String, String> requestParams)
            throws InvalidFacebookVerificationTokenException {
        FacebookVerificationToken token = new FacebookVerificationToken(requestParams);
        token.validate();

        return new ResponseEntity<>(token.getChallenge(), HttpStatus.OK);
    }

    /**
     * Entry point for messages sent to our Facebook page.
     *
     * @param payload the message payload sent by facebook
     */
    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody Payload payload) {
        facebookWebhookService.processPayload(payload);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
