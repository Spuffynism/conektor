package xyz.ndlr.model.provider.facebook.sendAPI;

import org.springframework.stereotype.Component;
import xyz.ndlr.model.dispatching.SupportedProvider;
import xyz.ndlr.model.entity.Account;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.sendAPI.message.Message;
import xyz.ndlr.model.provider.facebook.sendAPI.message.TextMessage;
import xyz.ndlr.model.provider.facebook.sendAPI.recipient.Recipient;

@Component
public class PayloadFactory {

    public SendablePayload getPayload(String recipientId, String message) {
        Recipient recipient = new Recipient(recipientId);
        Message textMessage = new TextMessage(message);

        return new SendablePayload(recipient, textMessage);
    }

    public SendablePayload getPayload(ProviderResponse response, SupportedProvider supportedProvider) {
        Account userProviderAccount = response.getUser().getAccount(supportedProvider);

        return getPayload(userProviderAccount.getToken(), response.getMessage());
    }
}
