package xyz.ndlr.model.provider.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.InvalidFacebookVerificationToken;

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

    public FacebookVerificationToken(Map<String, String> requestParams) {
        this.mode = requestParams.get("hub.mode");
        this.challenge = requestParams.get("hub.challenge");
        this.tokenFromFacebook = requestParams.get("hub.verify_token");
    }

    public void validate() throws InvalidFacebookVerificationToken {
        if (mode == null || tokenFromFacebook == null || !mode.equalsIgnoreCase(SUBSCRIBE_MODE) ||
                !actualValidToken.equals(tokenFromFacebook)) {
            throw new InvalidFacebookVerificationToken();
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
