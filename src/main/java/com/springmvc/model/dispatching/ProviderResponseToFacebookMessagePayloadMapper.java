package com.springmvc.model.dispatching;

import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.sendAPI.Payload;
import com.springmvc.model.provider.facebook.sendAPI.PayloadFactory;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ProviderResponseToFacebookMessagePayloadMapper implements BiFunction<ProviderResponse,
        String, Payload> {
    private static final PayloadFactory payloadFactory = new PayloadFactory();

    public List<Payload> apply(List<ProviderResponse> providerResponses, String recipientId) {
        return providerResponses
                .stream()
                .map(x -> this.apply(x, recipientId))
                .collect(Collectors.toList());
    }

    @Override
    public Payload apply(ProviderResponse providerResponse, String recipientId) {
        return payloadFactory.getPayload(recipientId, providerResponse.getMessage());
    }
}
