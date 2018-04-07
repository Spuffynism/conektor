package xyz.ndlr.model.dispatching;

import org.springframework.stereotype.Component;
import xyz.ndlr.model.entity.Account;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.sendAPI.MessageSendablePayload;
import xyz.ndlr.model.provider.facebook.sendAPI.SendablePayload;
import xyz.ndlr.model.provider.facebook.sendAPI.recipient.Recipient;

import java.util.function.Function;

@Component
public class ProviderResponseToSendablePayloadAdapter implements Function<ProviderResponse,
        SendablePayload> {

    @Override
    public SendablePayload apply(ProviderResponse providerResponse) {
        return getPayload(providerResponse, SupportedProvider.FACEBOOK);
    }

    public SendablePayload getPayload(ProviderResponse response, SupportedProvider
            supportedProvider) {
        Account userProviderAccount = response.getUser().getAccount(supportedProvider);
        Recipient recipient = new Recipient(userProviderAccount.getToken());

        return new MessageSendablePayload(recipient, response.getMessage());
    }
}
