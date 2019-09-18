package xyz.ndlr.infrastructure.provider.ping_pong;

import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.SimpleService;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.mapping.ProviderMapping;
import xyz.ndlr.infrastructure.provider.facebook.PipelinedMessage;

@ProviderMapping("ping")
public class PingPongService implements SimpleService {

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, "pong");
    }
}
