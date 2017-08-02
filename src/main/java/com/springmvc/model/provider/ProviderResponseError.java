package com.springmvc.model.provider;

import com.springmvc.model.entity.User;

public class ProviderResponseError extends ProviderResponse {
    public ProviderResponseError(User user, String message) {
        super(user, message);
    }
}
