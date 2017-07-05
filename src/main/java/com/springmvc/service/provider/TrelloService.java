package com.springmvc.service.provider;

import com.springmvc.model.provider.trello.TrelloArgument;
import com.springmvc.model.provider.trello.TrelloResponse;

import java.util.List;

public class TrelloService {

    public TrelloResponse add(List<TrelloArgument> arguments) {
        String message = "You sent a message to Trello. The arguments were:\n";
        for (TrelloArgument arg : arguments) {
            message += String.format("%s : %s\n", arg.getAction(), arg.getValue());
        }

        return new TrelloResponse(message);
    }

    public TrelloResponse remove(List<TrelloArgument> arguments) {
        return null;
    }

    public TrelloResponse getBoards() {
        return null;
    }

    public TrelloResponse getListsFromBoard() {
        return null;
    }

    public TrelloResponse getCards() {
        return null;
    }
}
