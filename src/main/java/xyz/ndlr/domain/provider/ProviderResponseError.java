package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.user.User;

public class ProviderResponseError extends ProviderResponse {
    public ProviderResponseError(User user, String message) {
        super(user, message);
    }
}
