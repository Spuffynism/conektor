package com.springmvc.model.provider.trello;

import com.springmvc.model.entity.User;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
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