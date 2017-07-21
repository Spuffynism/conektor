package com.springmvc.model.provider.facebook.sendAPI;

import com.springmvc.model.provider.facebook.sendAPI.message.Message;
import com.springmvc.model.provider.facebook.sendAPI.message.TextMessage;
import com.springmvc.model.provider.facebook.sendAPI.recipient.Recipient;

public class PayloadFactory {
    public PayloadFactory() {
    }

    public SendPayload getPayload(String senderId, String message) {
        Recipient recipient = new Recipient(senderId);
        Message textMessage = new TextMessage(message);

        return new SendPayload(recipient, textMessage);
    }
}
