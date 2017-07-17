package com.springmvc.service.provider;

import com.springmvc.model.provider.trello.TrelloResponse;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class TrelloService {

    @NotNull
    public TrelloResponse add(Map<String, String> arguments) {
        String message = "You sent a message to Trello. The arguments were:\n";
        for (String arg : arguments.keySet()) {
            message += String.format("%s : %s\n", arg, arguments.get(arg));
        }

        return new TrelloResponse(message);
    }

    @NotNull
    public TrelloResponse remove(Map<String, String> arguments) {
        return new TrelloResponse("no response");
    }

    @NotNull
    public TrelloResponse getBoards(Map<String, String> arguments) {
        return new TrelloResponse("no response");
    }

    @NotNull
    public TrelloResponse getListsFromBoard(Map<String, String> arguments) {
        return new TrelloResponse("no response");
    }

    @NotNull
    public TrelloResponse getCards(Map<String, String> arguments) {
        return new TrelloResponse("no response");
    }
}
