package xyz.ndlr.model.provider;

import xyz.ndlr.model.entity.User;

public class ProviderResponseError extends ProviderResponse {
    public ProviderResponseError(User user, String message) {
        super(user, message);
    }
}
