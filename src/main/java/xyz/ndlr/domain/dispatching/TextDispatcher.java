package xyz.ndlr.domain.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.ProviderResponseQueue;
import xyz.ndlr.domain.dispatching.mapping.Action;
import xyz.ndlr.domain.dispatching.mapping.ActionRepository;
import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.parsing.MessageParser;
import xyz.ndlr.domain.parsing.ParsedMessage;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;
import xyz.ndlr.domain.provider.facebook.webhook.Messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class TextDispatcher extends AbstractSubDispatcher {
    @Autowired
    public TextDispatcher(ActionRepository actionRepository,
                          ProviderResponseQueue sharedResponses) {
        super(actionRepository, sharedResponses);
    }

    @Override
    public Stream<ProviderResponse> onDispatchAndQueue(User user, Messaging messaging) throws
            CannotDispatchException {
        ParsedMessage parsedMessage;

        try {
            parsedMessage = tryGetParsedMessage(messaging);
        } catch (IllegalArgumentException e) {
            throw new CannotDispatchException(e.getMessage());
        }

        PipelinedMessage pipelinedMessage = new PipelinedMessage(messaging, parsedMessage);

        return dispatch(user, pipelinedMessage).stream();
    }

    private List<ProviderResponse> dispatch(User user, PipelinedMessage pipelinedMessage) throws
            CannotDispatchException {
        ParsedMessage parsedMessage = pipelinedMessage.getParsedMessage();
        Map<String, String> arguments = parsedMessage.getArguments();

        List<ProviderResponse> responses = new ArrayList<>();

        boolean useDefaultAction = arguments.isEmpty();
        if (useDefaultAction) {
            Action action = actionRepository.getDefault(parsedMessage.getCommand());

            responses.add(apply(action, user, pipelinedMessage));
        } else {
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                Action action = actionRepository.get(parsedMessage.getCommand(), entry.getKey());

                responses.add(apply(action, user, pipelinedMessage));
            }
        }

        return responses;
    }

    private static ParsedMessage tryGetParsedMessage(Messaging messaging) throws
            IllegalArgumentException {
        MessageParser parser = MessageParser.fromMessage(messaging.getMessage());

        return parser.getParsedMessage();
    }

    private ProviderResponse apply(Action action, User user, PipelinedMessage pipelinedMessage)
            throws CannotDispatchException {
        if (action == null)
            throw new CannotDispatchException("invalid action");

        return action.apply(user, pipelinedMessage);
    }
}
