package xyz.ndlr.infrastructure.dispatching;

import org.springframework.stereotype.Component;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.MessageSendablePayload;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.SendablePayload;
import xyz.ndlr.infrastructure.provider.facebook.sendAPI.recipient.Recipient;

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
