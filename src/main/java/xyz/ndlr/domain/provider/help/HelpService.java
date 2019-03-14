package xyz.ndlr.domain.provider.help;

import xyz.ndlr.domain.dispatching.mapping.ProviderMapping;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.SimpleService;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;
import xyz.ndlr.domain.user.User;

@ProviderMapping("help")
public class HelpService implements SimpleService {

    //TODO
    private static final String HELP_MESSAGE = "";

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, HELP_MESSAGE);
    }
}
