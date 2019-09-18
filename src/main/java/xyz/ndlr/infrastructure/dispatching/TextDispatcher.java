package xyz.ndlr.infrastructure.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.exception.CannotDispatchException;
import xyz.ndlr.domain.exception.InvalidActionException;
import xyz.ndlr.domain.parsing.MessageParser;
import xyz.ndlr.domain.parsing.ParsedMessage;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.ProviderResponseQueue;
import xyz.ndlr.infrastructure.mapping.Action;
import xyz.ndlr.infrastructure.mapping.ActionRepository;
import xyz.ndlr.infrastructure.provider.facebook.PipelinedMessage;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Messaging;

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

        // TODO(nich): Create and use PipelinedMessageFactory
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
            throw new InvalidActionException();

        return action.apply(user, pipelinedMessage);
    }
}
