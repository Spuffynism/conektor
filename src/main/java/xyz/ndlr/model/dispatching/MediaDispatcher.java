package xyz.ndlr.model.dispatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.sendAPI.message.TextMessage;
import xyz.ndlr.model.provider.facebook.sendAPI.message.quick_reply.QuickReplyMessageFactory;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Message;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

import java.util.List;

@Component
public class MediaDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private QuickReplyMessageFactory quickReplyMessageFactory;

    @Autowired
    MediaDispatcher(ActionRepository actionRepository,
                    ProviderResponseQueue sharedResponses,
                    QuickReplyMessageFactory quickReplyMessageFactory) {
        super(actionRepository, sharedResponses);
        this.quickReplyMessageFactory = quickReplyMessageFactory;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) {
        Message message = messaging.getMessage();

        if (message.contains(AttachmentType.IMAGE)) {
            List<String> urls = message.getAttachmentURLs(AttachmentType.IMAGE);
            for (String url : urls) {
                TextMessage quickReplyMessage = quickReplyMessageFactory.generateForProviders(url,
                        actionRepository.getImageProviderHumanNames());
                queueResponse(new ProviderResponse(user, quickReplyMessage));
            }
        } else {
            queueResponse(ProviderResponse.notImplemented(user));
        }
    }
}
