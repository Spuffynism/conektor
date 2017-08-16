package xyz.ndlr.model.dispatching;

import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.parsing.MessageParser;
import xyz.ndlr.model.parsing.ParsedMessage;
import xyz.ndlr.model.provider.AbstractProviderDispatcher;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
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
