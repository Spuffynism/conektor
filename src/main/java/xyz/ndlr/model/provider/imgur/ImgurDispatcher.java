package xyz.ndlr.model.provider.imgur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.AbstractProviderDispatcher;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.webhook.Message;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

import java.util.concurrent.CompletableFuture;

@Component
public class ImgurDispatcher extends AbstractProviderDispatcher<Messaging> {
    private final ImgurService imgurService;

    @Autowired
    public ImgurDispatcher(ImgurService imgurService) {
        this.imgurService = imgurService;
    }

    @Override
    public CompletableFuture<ProviderResponse> dispatch(User user, Messaging messaging) throws
            IllegalArgumentException {
        Message message = messaging.getMessage();
        return imgurService.upload(message);
    }
}
