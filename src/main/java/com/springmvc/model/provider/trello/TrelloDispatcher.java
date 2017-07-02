package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.service.provider.TrelloService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class TrelloDispatcher extends AbstractProviderDispatcher<TrelloArgument, TrelloResponse> {
    private static TrelloService trelloService = new TrelloService();

    public TrelloDispatcher() {
        super(TrelloArgument.class);
    }

    protected List<TrelloResponse> dispatch(List<TrelloArgument> arguments) throws
            InvalidArgumentException {
        List<TrelloResponse> responses = new ArrayList<>();

        // TODO Add cases for all possible TrelloActions that can be done on a provider
        for (TrelloArgument arg : arguments) {
            switch (TrelloAction.valueOf(arg.getAction())) {
                case ADD_CARD_TO_LIST:
                    responses.add(trelloService.addCartToList(arguments));
                    break;
            }
        }

        if (responses.isEmpty())
            throw new InvalidArgumentException(new String[]{});

        return responses;
    }
}
