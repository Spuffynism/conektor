package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.IProviderDispatcher;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.service.provider.TrelloService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Configurable
public class TrelloDispatcher implements IProviderDispatcher<TrelloArgument, TrelloResponse> {
    private static TrelloService trelloService = new TrelloService();

    // TODO Maybe make this code more generic by having it in a higher-class
    @Override
    public List<TrelloResponse> act(Map<String, String> arguments) throws InvalidArgumentException {
        List<TrelloArgument> trelloArguments = new ArrayList<>();
        for (String key : arguments.keySet())
            trelloArguments.add(new TrelloArgument(key, arguments.get(key)));

        return act(trelloArguments);
    }

    @Override
    public List<TrelloResponse> act(List<TrelloArgument> arguments) throws
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
