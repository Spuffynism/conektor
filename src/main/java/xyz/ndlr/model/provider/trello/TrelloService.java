package xyz.ndlr.model.provider.trello;

import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Service
public class TrelloService {

    @NotNull
    public ProviderResponse add(User user, PipelinedMessage pipelinedMessage) {
        String message = "You sent a message to Trello. The arguments were:\n";
        Map<String, String> arguments = pipelinedMessage.getParsedMessage().getArguments();
        for (String arg : arguments.keySet()) {
            message += String.format("%s : %s\n", arg, arguments.get(arg));
        }

        return new ProviderResponse(user, message);
    }

    @NotNull
    public ProviderResponse remove(User user, PipelinedMessage pipelinedMessage) {
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
