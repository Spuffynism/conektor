package xyz.ndlr.infrastructure.provider.facebook;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FacebookVerificationTokenFactory {
    // TODO(nich): The FacebookVerificationToken should take three arguments instead of a map
    public FacebookVerificationToken createFromRequestParameters(
            Map<String, String> requestParameters) {
        return new FacebookVerificationToken(requestParameters);
    }
}
