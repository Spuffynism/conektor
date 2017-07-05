package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.service.provider.TrelloService;

import java.util.ArrayList;
import java.util.List;


public class TrelloDispatcher extends AbstractProviderDispatcher<TrelloArgument, TrelloResponse> {
    private static TrelloService trelloService = new TrelloService();

    public TrelloDispatcher() {
        super(TrelloArgument.class);
    }

    protected List<TrelloResponse> dispatch(List<TrelloArgument> arguments) throws
            IllegalArgumentException {
        List<TrelloResponse> responses = new ArrayList<>();

        String firstAction = arguments.get(0).getAction();

        switch (TrelloAction.valueOf(firstAction.toUpperCase())) {
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
            throw new IllegalArgumentException("no responses");

        return responses;
    }
}
