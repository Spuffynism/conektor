package xyz.ndlr.model.dispatching;

import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.sendAPI.PayloadFactory;
import xyz.ndlr.model.provider.facebook.sendAPI.SendablePayload;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProviderResponseToSendPayloadAdapter implements Function<ProviderResponse,
        SendablePayload> {
    private final PayloadFactory payloadFactory;

    public ProviderResponseToSendPayloadAdapter(PayloadFactory payloadFactory) {
        this.payloadFactory = payloadFactory;
    }

    @Override
    public SendablePayload apply(ProviderResponse providerResponse) {
        return payloadFactory.getPayload(providerResponse, SupportedProvider.FACEBOOK);
    }
}
