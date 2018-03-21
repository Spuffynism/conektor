package xyz.ndlr.model.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.Action;
import xyz.ndlr.model.dispatching.mapping.ProviderActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.parsing.MessageParser;
import xyz.ndlr.model.parsing.ParsedMessage;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class TextDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    @Autowired
    public TextDispatcher(ProviderActionRepository providerActionRepository,
                          ProviderResponseQueue sharedResponses) {
        super(providerActionRepository, sharedResponses);
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        ParsedMessage parsedMessage;

        try {
            parsedMessage = tryGetParsedMessage(messaging);
        } catch (IllegalArgumentException e) {
            throw new CannotDispatchException(e.getMessage());
        }

        PipelinedMessage pipelinedMessage = new PipelinedMessage(messaging, parsedMessage);

        for (Map.Entry<String, String> entry : parsedMessage.getArguments().entrySet()) {
            Action action = providerActionRepository.get(parsedMessage.getCommand(),
                    entry.getKey());

            if (action == null) {
                throw new CannotDispatchException("invalid action");
            }

            ProviderResponse response = action.apply(user, pipelinedMessage);

            CompletableFuture.completedFuture(response)
                    .thenAccept(this::queueResponse);
        }
    }

    private static ParsedMessage tryGetParsedMessage(Messaging messaging) throws
            IllegalArgumentException {
        MessageParser parser = new MessageParser(messaging.getMessage());

        return parser.getParsedMessage();
    }
}
