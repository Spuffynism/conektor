package xyz.ndlr.model.provider.ping_pong;

import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.dispatching.mapping.ProviderMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

@ProviderMapping("ping")
public class PingPongService implements BiFunction<User, PipelinedMessage, ProviderResponse> {

    @ActionMapping("")
    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, "pong");
    }
}
