package com.springmvc.model.provider.imgur;

import com.springmvc.model.entity.User;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.webhook.Message;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
