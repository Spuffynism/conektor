package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;
import xyz.ndlr.infrastructure.provider.facebook.FacebookVerificationToken;

import java.util.Map;

@Service
public class FacebookWebhookSubscriptionService {

    public String subscribe(Map<String, String> requestParams)
            throws InvalidFacebookVerificationTokenException {
        FacebookVerificationToken token = new FacebookVerificationToken(requestParams);

        token.validate();

        return token.getChallenge();
    }
}
