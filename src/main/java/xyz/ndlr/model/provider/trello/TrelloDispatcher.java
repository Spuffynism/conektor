package xyz.ndlr.model.provider.trello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.AbstractProviderDispatcher;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.util.concurrent.CompletableFuture;

@Component
public class TrelloDispatcher extends AbstractProviderDispatcher<PipelinedMessage> {
    private final TrelloService trelloService;

    @Autowired
    public TrelloDispatcher(TrelloService trelloService) {
        this.trelloService = trelloService;
    }

    public CompletableFuture<ProviderResponse> dispatch(User user,
                                                        PipelinedMessage pipelinedMessage)
            throws IllegalArgumentException {
        ProviderResponse response = null;

        /*TrelloAction action = getFirstAction(pipelinedMessage.getParsedMessage().getArguments(),
                TrelloAction.class);
        switch (action) {
            case ADD:
                response = trelloService.add(user, pipelinedMessage);
                break;
            case DELETE:
            case REMOVE:
                response = trelloService.remove(user, pipelinedMessage);
                break;
            default:
                break;
        }*/

        if (response == null)
            throw new IllegalArgumentException("no response from provider");

        CompletableFuture<ProviderResponse> future = new CompletableFuture<>();
        future.complete(response);

        return future;
    }
}