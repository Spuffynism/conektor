package xyz.ndlr.model.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Message;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.provider.imgur.ImgurService;

@Component
public class MediaDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private ImgurService imgurService;

    @Autowired
    MediaDispatcher(ActionRepository actionRepository,
                    ProviderResponseQueue sharedResponses,
                    ImgurService imgurService) {
        super(actionRepository, sharedResponses);
        this.imgurService = imgurService;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) {
        Message message = messaging.getMessage();
        if (message.contains(AttachmentType.IMAGE)) {
            imgurService.upload(user, message)
                    .thenAccept(this::queueResponse);
        }
    }
}
