package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.ProviderResponseQueue;
import com.springmvc.model.entity.User;
import com.springmvc.model.parsing.MessageParser;
import com.springmvc.model.parsing.ParsedMessage;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        AbstractProviderDispatcher dispatcher = providerDispatcherFactory
                .getFromDestinationProvider(parsedMessage.getCommand());

        //TODO
        //queueResponse(dispatcher.dispatchAndQueue(user, pipelinedMessage));
    }

    private ParsedMessage tryGetParsedMessage(Messaging messaging) {
        MessageParser parser;

        try {
            parser = new MessageParser(messaging.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return parser.getParsedMessage();
    }
}
