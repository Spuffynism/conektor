package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.xml.ws.ServiceMode;
import java.util.Map;

@Service
public class TrelloService {

    @NotNull
    public ProviderResponse add(PipelinedMessage pipelinedMessage) {
        String message = "You sent a message to Trello. The arguments were:\n";
        Map<String, String> arguments = pipelinedMessage.getParsedMessage().getArguments();
        for (String arg : arguments.keySet()) {
            message += String.format("%s : %s\n", arg, arguments.get(arg));
        }

        return new ProviderResponse(message);
    }

    @NotNull
    public ProviderResponse remove(PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("no response");
    }

    @NotNull
    public ProviderResponse getBoards(PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("no response");
    }

    @NotNull
    public ProviderResponse getListsFromBoard(PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("no response");
    }

    @NotNull
    public ProviderResponse getCards(PipelinedMessage pipelinedMessage) {
        return new ProviderResponse("no response");
    }
}
