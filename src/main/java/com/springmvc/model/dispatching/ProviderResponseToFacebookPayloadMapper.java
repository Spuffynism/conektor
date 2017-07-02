package com.springmvc.model.dispatching;

import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.FacebookResponsePayload;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProviderResponseToFacebookPayloadMapper implements Function<IProviderResponse,
        FacebookResponsePayload> {

    public List<FacebookResponsePayload> apply(List<IProviderResponse> providerResponses) {
        return providerResponses
                .stream()
                .map(this)
                .collect(Collectors.toList());
    }

    @Override
    public FacebookResponsePayload apply(IProviderResponse providerResponse) {
        FacebookResponsePayload responseForUser = new FacebookResponsePayload();
        responseForUser.setMessage(providerResponse.getMessage());

        return responseForUser;
    }
}
