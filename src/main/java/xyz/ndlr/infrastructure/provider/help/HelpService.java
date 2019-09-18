package xyz.ndlr.infrastructure.provider.help;

import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.ProviderResponseFactory;
import xyz.ndlr.domain.provider.SimpleService;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.mapping.ProviderMapping;
import xyz.ndlr.infrastructure.provider.facebook.PipelinedMessage;

@ProviderMapping("help")
public class HelpService implements SimpleService {
    //TODO(nich)
    private static final String HELP_MESSAGE = "";

    private ProviderResponseFactory providerResponseFactory;

    public HelpService(ProviderResponseFactory providerResponseFactory) {
        this.providerResponseFactory = providerResponseFactory;
    }

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return providerResponseFactory.createForUser(user, HELP_MESSAGE);
    }
}
