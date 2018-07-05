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
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.provider.facebook.webhook.event.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MediaDispatcher extends AbstractSubDispatcher {
    private QuickReplyMessageFactory quickReplyMessageFactory;

    @Autowired
    MediaDispatcher(ActionRepository actionRepository,
                    ProviderResponseQueue sharedResponses,
                    QuickReplyMessageFactory quickReplyMessageFactory) {
        super(actionRepository, sharedResponses);
        this.quickReplyMessageFactory = quickReplyMessageFactory;
    }

    @Override
    public Stream<ProviderResponse> onDispatchAndQueue(User user, Messaging messaging) {
        Message message = messaging.getMessage();

        List<ProviderResponse> responses = new ArrayList<>();
        if (message.contains(AttachmentType.IMAGE)) {
            List<String> urls = message.getAttachmentURLs(AttachmentType.IMAGE);
            for (String url : urls) {
                TextMessage quickReplyMessage = quickReplyMessageFactory.generateForProviders(url,
                        actionRepository.getImageProviderHumanNames());
                responses.add(new ProviderResponse(user, quickReplyMessage));
            }
        } else {
            responses.add(ProviderResponse.notImplemented(user));
        }

        return responses.stream();
    }
}
