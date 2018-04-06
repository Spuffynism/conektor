package xyz.ndlr.model.provider.ping_pong;

import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.SimpleService;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

@ProviderMapping("ping")
public class PingPongService implements SimpleService {

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, "pong");
    }
}
