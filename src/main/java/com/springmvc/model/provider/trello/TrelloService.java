package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.ProviderResponse;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class TrelloService {

    @NotNull
    public ProviderResponse add(Map<String, String> arguments) {
        String message = "You sent a message to Trello. The arguments were:\n";
        for (String arg : arguments.keySet()) {
            message += String.format("%s : %s\n", arg, arguments.get(arg));
        }

        return new ProviderResponse(message);
    }

    @NotNull
    public ProviderResponse remove(Map<String, String> arguments) {
        return new ProviderResponse("no response");
    }

    @NotNull
    public ProviderResponse getBoards(Map<String, String> arguments) {
        return new ProviderResponse("no response");
    }

    @NotNull
    public ProviderResponse getListsFromBoard(Map<String, String> arguments) {
        return new ProviderResponse("no response");
    }

    @NotNull
    public ProviderResponse getCards(Map<String, String> arguments) {
        return new ProviderResponse("no response");
    }
}
