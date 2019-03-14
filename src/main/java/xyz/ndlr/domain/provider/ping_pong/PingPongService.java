package xyz.ndlr.domain.provider.ping_pong;

import xyz.ndlr.domain.dispatching.mapping.ProviderMapping;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.SimpleService;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;
import xyz.ndlr.domain.user.User;

@ProviderMapping("ping")
public class PingPongService implements SimpleService {

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, "pong");
    }
}
