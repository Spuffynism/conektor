package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.entity.User;
import com.springmvc.model.parsing.MessageParser;
import com.springmvc.model.parsing.ParsedMessage;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.facebook.sendAPI.SendPayload;
import com.springmvc.model.provider.facebook.webhook.Messaging;

import java.util.List;

public class MainDispatcher implements IMessagingDispatcher {
    private List<ProviderResponse> responses; // TODO use producer-consumer pattern on this
    private User user;
    private String facebookSenderId;
    private ProviderResponseToSendPayloadMapper mapper;

    public MainDispatcher(User user, String facebookSenderId) {
        this.user = user;
        this.facebookSenderId = facebookSenderId;
        this.mapper = new ProviderResponseToSendPayloadMapper();
    }

    @Override
    public void dispatch(Messaging messaging) throws CannotDispatchException {
        if (user == null)
            throw new CannotDispatchException("user cannot be null - a message needs a " +
                    "destination");

        if (messaging.getMessage().containsMedia()) {
            new MediaDispatcher(responses).dispatch(messaging);
        } else {
            new TextDispatcher(responses).dispatch(messaging);
        }
    }

    public List<SendPayload> collectFacebookResponsePayloads() {
        return mapper.apply(responses, facebookSenderId);
    }
}
