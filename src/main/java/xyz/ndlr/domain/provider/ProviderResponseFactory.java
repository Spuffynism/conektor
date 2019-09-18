package xyz.ndlr.domain.provider;

import org.springframework.stereotype.Component;
import xyz.ndlr.domain.user.User;

@Component
public class ProviderResponseFactory {
    public ProviderResponse createForUser(User user, String message) {
        return new ProviderResponse(user, message);
    }
}
