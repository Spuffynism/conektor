package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;
import xyz.ndlr.infrastructure.provider.facebook.Challenge;
import xyz.ndlr.infrastructure.provider.facebook.FacebookVerificationToken;
import xyz.ndlr.infrastructure.provider.facebook.FacebookVerificationTokenFactory;

import java.util.Map;

@Service
public class FacebookWebhookSubscriptionService {

    private FacebookVerificationTokenFactory facebookVerificationTokenFactory;

    public FacebookWebhookSubscriptionService(
            FacebookVerificationTokenFactory facebookVerificationTokenFactory) {
        this.facebookVerificationTokenFactory = facebookVerificationTokenFactory;
    }

    public Challenge subscribe(Map<String, String> requestParameters)
            throws InvalidFacebookVerificationTokenException {
        FacebookVerificationToken token = this.facebookVerificationTokenFactory
                .createFromRequestParameters(requestParameters);

        token.validate();

        return token.getChallenge();
    }
}
