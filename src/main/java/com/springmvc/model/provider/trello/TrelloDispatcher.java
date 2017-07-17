package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.service.provider.TrelloService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TrelloDispatcher extends AbstractProviderDispatcher<TrelloResponse> {
    private static final TrelloService trelloService = new TrelloService();

    public List<TrelloResponse> dispatch(Map<String, String> arguments) throws
            IllegalArgumentException {
        List<TrelloResponse> responses = new ArrayList<>();

        switch (getFirstAction(arguments, TrelloAction.class)) {

            case ADD:
                responses.add(trelloService.add(arguments));
                break;
            case DELETE:
            case REMOVE:
                responses.add(trelloService.remove(arguments));
                break;
            default:
                break;
        }

        if (responses.isEmpty())
            throw new IllegalArgumentException("no response from provider");

        return responses;
    }
}