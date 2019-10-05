package xyz.ndlr.infrastructure.provider.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;

import java.util.Map;

@Component
public class FacebookVerificationToken {

    private static final String HUB_MODE_PARAMETER = "hub.mode";
    private static final String HUB_CHALLENGE_PARAMETER = "hub.challenge";
    private static final String HUB_VERIFY_TOKEN_PARAMETER = "hub.verify_token";
    private static final String SUBSCRIBE_MODE = "subscribe";

    private static String verifyToken;

    private String mode;
    private Challenge challenge;
    private String tokenFromFacebook;

    // TODO(nich): Is this public constructor necessary?
    public FacebookVerificationToken() {
    }

    public FacebookVerificationToken(Map<String, String> requestParameters) {
        this.mode = requestParameters.get(HUB_MODE_PARAMETER);
        this.challenge = Challenge.fromString(requestParameters.get(HUB_CHALLENGE_PARAMETER));
        this.tokenFromFacebook = requestParameters.get(HUB_VERIFY_TOKEN_PARAMETER);
    }

    public void validate() throws InvalidFacebookVerificationTokenException {
        if (mode == null || !mode.equalsIgnoreCase(SUBSCRIBE_MODE) ||
                !verifyToken.equals(tokenFromFacebook)) {
            throw new InvalidFacebookVerificationTokenException();
        }
    }

    // TODO(nich): This value should be injected not so deep in here
    @Value("${facebook.verify_token}")
    private void setVerifyToken(String token) {
        verifyToken = token;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
