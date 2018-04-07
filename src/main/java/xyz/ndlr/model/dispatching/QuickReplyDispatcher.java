package xyz.ndlr.model.dispatching;

import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotDispatchException;
import xyz.ndlr.model.ProviderResponseQueue;
import xyz.ndlr.model.dispatching.mapping.ActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.sendAPI.message.TextMessage;
import xyz.ndlr.model.provider.facebook.sendAPI.message.quick_reply.QuickReplyMessageFactory;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;

import java.util.List;

@Component
public class QuickReplyDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {

    private QuickReplyMessageFactory quickReplyMessageFactory;

    public QuickReplyDispatcher(ActionRepository actionRepository,
                                ProviderResponseQueue sharedResponses,
                                QuickReplyMessageFactory quickReplyMessageFactory) {
        super(actionRepository, sharedResponses);
        this.quickReplyMessageFactory = quickReplyMessageFactory;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) throws CannotDispatchException {
        // TODO Set url as attachment after user quick replied
        if (messaging.getMessage().contains(AttachmentType.IMAGE)) {
            List<String> urls = messaging.getMessage().getAttachmentURLs(AttachmentType.IMAGE);
            for (String url : urls) {
                TextMessage quickReplyMessage =
                        quickReplyMessageFactory.generateForProviders(url,
                                actionRepository.getProviderHumanNames());
            }

        } else {
            queueResponse(ProviderResponse.notImplemented(user));
        }
    }
}
