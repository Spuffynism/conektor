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
import xyz.ndlr.model.provider.imgur.ImgurService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MediaDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {
    private ImgurService imgurService;
    private QuickReplyMessageFactory quickReplyMessageFactory;

    @Autowired
    MediaDispatcher(ActionRepository actionRepository,
                    ProviderResponseQueue sharedResponses,
                    ImgurService imgurService,
                    QuickReplyMessageFactory quickReplyMessageFactory) {
        super(actionRepository, sharedResponses);
        this.imgurService = imgurService;
        this.quickReplyMessageFactory = quickReplyMessageFactory;
    }

    @Override
    public void dispatchAndQueue(User user, Messaging messaging) {
        Message message = messaging.getMessage();
        /*if (message.contains(AttachmentType.IMAGE)) {

            imgurService.upload(user, message)
                    .thenAccept(this::queueResponse);
        }*/

        //TODO Populate & Use ActionRepository instead
        Map<String, String> providerHumanNames = new HashMap<>();
        providerHumanNames.put("imgur", "Imgur");
        providerHumanNames.put("mathpix", "MathPix");

        if (message.contains(AttachmentType.IMAGE)) {
            List<String> urls = message.getAttachmentURLs(AttachmentType.IMAGE);
            for (String url : urls) {
                TextMessage quickReplyMessage =
                        quickReplyMessageFactory.generateForProviders(url, providerHumanNames);
                queueResponse(new ProviderResponse(user, quickReplyMessage));
            }

        } else {
            queueResponse(ProviderResponse.notImplemented(user));
        }
    }
}
