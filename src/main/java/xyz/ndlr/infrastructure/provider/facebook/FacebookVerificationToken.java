package xyz.ndlr.infrastructure.provider.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;

import java.util.Map;

@Component
public class FacebookVerificationToken {
    private static String actualValidToken;
    private final static String SUBSCRIBE_MODE = "subscribe";

    private String mode;
    private String challenge;
    private String tokenFromFacebook;

    public FacebookVerificationToken() {
    }

    public FacebookVerificationToken(Map<String, String> requestParameters) {
        this.mode = requestParameters.get("hub.mode");
        this.challenge = requestParameters.get("hub.challenge");
        this.tokenFromFacebook = requestParameters.get("hub.verify_token");
    }

    public void validate() throws InvalidFacebookVerificationTokenException {
        if (mode == null || !mode.equalsIgnoreCase(SUBSCRIBE_MODE) ||
                !actualValidToken.equals(tokenFromFacebook)) {
            throw new InvalidFacebookVerificationTokenException();
        }
    }

    @Value("${facebook.verify_token}")
    private void setActualValidToken(String token) {
        actualValidToken = token;
    }

    public String getChallenge() {
        return challenge;
    }
}
