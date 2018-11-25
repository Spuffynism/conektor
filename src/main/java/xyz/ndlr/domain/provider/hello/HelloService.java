package xyz.ndlr.domain.provider.hello;

import xyz.ndlr.domain.dispatching.mapping.ProviderMapping;
import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.SimpleService;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;

@ProviderMapping("hello")
public class HelloService implements SimpleService {

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return new ProviderResponse(user, "Hello!");
    }
}
