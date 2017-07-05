package com.springmvc.model.dispatching;

import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.FacebookResponsePayload;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ProviderResponseToFacebookPayloadMapper implements BiFunction<IProviderResponse,
        String,
        FacebookResponsePayload> {

    public List<FacebookResponsePayload> apply(List<IProviderResponse> providerResponses, String
            recipientId) {
        return providerResponses
                .stream()
                .map(x -> this.apply(x, recipientId))
                .collect(Collectors.toList());
    }

    @Override
    public FacebookResponsePayload apply(IProviderResponse providerResponse, String recipientId) {
        FacebookResponsePayload responseForUser = new FacebookResponsePayload();
        responseForUser.setMessage(providerResponse.getMessage());
        responseForUser.setRecipient(recipientId);

        return responseForUser;
    }
}
