package com.springmvc.model.provider.facebook.sendAPI;

import com.springmvc.model.dispatching.SupportedProvider;
import com.springmvc.model.entity.Account;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.sendAPI.message.Message;
import com.springmvc.model.provider.facebook.sendAPI.message.TextMessage;
import com.springmvc.model.provider.facebook.sendAPI.recipient.Recipient;
import org.springframework.stereotype.Component;

@Component
public class PayloadFactory {

    public SendablePayload getPayload(String recipientId, String message) {
        Recipient recipient = new Recipient(recipientId);
        Message textMessage = new TextMessage(message);

        return new SendablePayload(recipient, textMessage);
    }

    public SendablePayload getPayload(ProviderResponse response, SupportedProvider supportedProvider) {
        Account userProviderAccount = response.getUser().getAccount(supportedProvider);

        return getPayload(userProviderAccount.getDetails(), response.getMessage());
    }
}
