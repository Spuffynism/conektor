package xyz.ndlr.model.provider.trello;

import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.AbstractProviderDispatcher;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class TrelloDispatcher extends AbstractProviderDispatcher<PipelinedMessage> {
    private final TrelloService trelloService;

    public TrelloDispatcher(TrelloService trelloService) {
        this.trelloService = trelloService;
    }

    public CompletableFuture<ProviderResponse> dispatch(User user,
                                                        PipelinedMessage pipelinedMessage)
            throws IllegalArgumentException {
        ProviderResponse response = null;

        switch (getFirstAction(pipelinedMessage.getParsedMessage().getArguments(),
                TrelloAction.class)) {
            case ADD:
                response = trelloService.add(user, pipelinedMessage);
                break;
            case DELETE:
            case REMOVE:
                response = trelloService.remove(user, pipelinedMessage);
                break;
            default:
                break;
        }

        if (response == null)
            throw new IllegalArgumentException("no response from provider");

        CompletableFuture<ProviderResponse> future = new CompletableFuture<>();
        future.complete(response);

        return future;
    }
}