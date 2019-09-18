package xyz.ndlr.infrastructure.provider.facebook;

import java.util.Map;

public class FacebookVerificationTokenFactory {
    public FacebookVerificationToken createFromRequestParameters(
            Map<String, String> requestParameters) {
        return new FacebookVerificationToken(requestParameters);
    }
}
