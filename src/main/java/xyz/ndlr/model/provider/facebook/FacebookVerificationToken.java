package xyz.ndlr.model.provider.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.InvalidFacebookVerificationToken;

import java.util.Map;

@Component
public class FacebookVerificationToken {
    private static String tokenFromHere;
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
                !tokenFromHere.equals(tokenFromFacebook))
            throw new InvalidFacebookVerificationToken();
    }

    @Value("${facebook.verify_token}")
    private void setTokenFromHere(String token) {
        tokenFromHere = token;
    }

    ///<editor-fold> getters & setters

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getTokenFromFacebook() {
        return tokenFromFacebook;
    }

    public void setTokenFromFacebook(String tokenFromFacebook) {
        this.tokenFromFacebook = tokenFromFacebook;
    }

    ///</editor-fold>
}
