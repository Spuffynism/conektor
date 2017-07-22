package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.parsing.MessageParser;
import com.springmvc.model.parsing.ParsedMessage;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.facebook.webhook.Messaging;

import java.util.List;

public class TextDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private static final ProviderDispatcherFactory providerDispatcherFactory
            = new ProviderDispatcherFactory();

    public TextDispatcher(List<ProviderResponse> responses) {
        super(responses);
    }

    @Override
    public void dispatch(Messaging messaging) throws CannotDispatchException {
        MessageParser parser;

        try {
            parser = new MessageParser(messaging.getMessage());
        } catch (IllegalArgumentException e) {
            throw new CannotDispatchException(e.getMessage());
        }

        ParsedMessage parsedMessage = parser.getParsedMessage();

        PipelinedMessage pipelinedMessage = new PipelinedMessage(messaging, parsedMessage);

        AbstractProviderDispatcher dispatcher = providerDispatcherFactory
                .getFromDestinationProvider(parsedMessage.getCommand());
        responses.addAll(dispatcher.dispatch(pipelinedMessage));
    }
}
