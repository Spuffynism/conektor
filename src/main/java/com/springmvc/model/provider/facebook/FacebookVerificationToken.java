package com.springmvc.model.provider.facebook;

import com.springmvc.exception.InvalidFacebookVerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FacebookVerificationToken {
    private static String tokenFromHere;
    private final static String subscribeMode = "subscribe";

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
        if (!getMode().equalsIgnoreCase(subscribeMode) || tokenFromHere == null ||
                tokenFromFacebook == null || !tokenFromHere.equals(tokenFromFacebook))
            throw new InvalidFacebookVerificationToken();
    }

    @Value("${facebook.verify_token}")
    private void setTokenFromHere(String token) {
        tokenFromHere = token;
    }

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
}
