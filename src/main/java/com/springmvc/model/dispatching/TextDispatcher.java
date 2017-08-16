package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.ProviderResponseQueue;
import com.springmvc.model.entity.User;
import com.springmvc.model.parsing.MessageParser;
import com.springmvc.model.parsing.ParsedMessage;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class TextDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    @Autowired
    public TextDispatcher(ProviderDispatcherFactory providerDispatcherFactory,
                          ProviderResponseQueue sharedResponses) {
        super(providerDispatcherFactory, sharedResponses);
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        ParsedMessage parsedMessage = tryGetParsedMessage(messaging);

        PipelinedMessage pipelinedMessage = new PipelinedMessage(messaging, parsedMessage);
        AbstractProviderDispatcher<PipelinedMessage> dispatcher = providerDispatcherFactory
                .getFromDestinationProvider(parsedMessage.getCommand());

        Consumer<ProviderResponse> acceptAndQueueResponse = this::queueResponse;

        dispatcher.dispatch(user, pipelinedMessage)
                .thenAccept(acceptAndQueueResponse);
    }

    private static ParsedMessage tryGetParsedMessage(Messaging messaging) {
        MessageParser parser;

        try {
            parser = new MessageParser(messaging.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return parser.getParsedMessage();
    }
}
