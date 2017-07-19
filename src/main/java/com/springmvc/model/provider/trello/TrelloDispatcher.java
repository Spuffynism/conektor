package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;

import java.util.ArrayList;
import java.util.List;


public class TrelloDispatcher extends AbstractProviderDispatcher {
    private static final TrelloService trelloService = new TrelloService();

    public List<ProviderResponse> dispatch(PipelinedMessage pipelinedMessage) throws
            IllegalArgumentException {
        List<ProviderResponse> responses = new ArrayList<>();

        switch (getFirstAction(pipelinedMessage.getParsedMessage().getArguments(),
                TrelloAction.class)) {
            case ADD:
                responses.add(trelloService.add(pipelinedMessage));
                break;
            case DELETE:
            case REMOVE:
                responses.add(trelloService.remove(pipelinedMessage));
                break;
            default:
                break;
        }

        if (responses.isEmpty())
            throw new IllegalArgumentException("no response from provider");

        return responses;
    }
}