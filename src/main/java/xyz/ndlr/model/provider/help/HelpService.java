package xyz.ndlr.model.provider.help;

import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.SimpleService;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

@ProviderMapping("help")
public class HelpService implements SimpleService {

    @ActionMapping("")
    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return null;
    }
}
