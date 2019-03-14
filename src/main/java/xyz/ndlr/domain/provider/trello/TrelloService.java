package xyz.ndlr.domain.provider.trello;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.ndlr.domain.dispatching.mapping.ActionMapping;
import xyz.ndlr.domain.dispatching.mapping.ProviderMapping;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;
import xyz.ndlr.domain.provider.facebook.sendAPI.message.TextMessage;
import xyz.ndlr.domain.user.User;

import java.util.Map;

@ProviderMapping("trello")
public class TrelloService {
    private final TrelloRepository trelloRepository;

    @Autowired
    TrelloService(TrelloRepository trelloRepository) {
        this.trelloRepository = trelloRepository;
    }

    @ActionMapping("test")
    public ProviderResponse test(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, new TextMessage(null));
    }

    @ActionMapping("add")
    public ProviderResponse add(User user, PipelinedMessage pipelinedMessage) {
        String message = "You sent a message to Trello. The arguments were:\n";
        Map<String, String> arguments = pipelinedMessage.getParsedMessage().getArguments();
        for (String arg : arguments.keySet()) {
            message += String.format("%s : %s\n", arg, arguments.get(arg));
        }

        return new ProviderResponse(user, message);
    }

    @ActionMapping({"remove", "delete"})
    public ProviderResponse remove(User user, PipelinedMessage pipelinedMessage) {
        return null;
    }

    @ActionMapping("boards")
    public ProviderResponse getBoards(User user, PipelinedMessage pipelinedMessage) {
        return null;
    }

    @ActionMapping("lists-from-board")
    public ProviderResponse getListsFromBoard(User user, PipelinedMessage pipelinedMessage) {
        return null;
    }

    @ActionMapping("cards")
    public ProviderResponse getCards(User user, PipelinedMessage pipelinedMessage) {
        return null;
    }

    @ActionMapping("switch-to")
    public ProviderResponse switchTo(User user, PipelinedMessage pipelinedMessage) {
        return null;
    }
}