package xyz.ndlr.infrastructure.provider.hello;

import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.ProviderResponseFactory;
import xyz.ndlr.domain.provider.SimpleService;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.mapping.ProviderMapping;
import xyz.ndlr.infrastructure.provider.facebook.PipelinedMessage;

@ProviderMapping("hello")
public class HelloService implements SimpleService {
    private ProviderResponseFactory providerResponseFactory;

    public HelloService(ProviderResponseFactory providerResponseFactory) {
        this.providerResponseFactory = providerResponseFactory;
    }

    @Override
    public ProviderResponse apply(User user, PipelinedMessage pipelinedMessage) {
        return providerResponseFactory.createForUser(user, "Hello!");
    }
}
