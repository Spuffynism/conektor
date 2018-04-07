package xyz.ndlr.model.provider.facebook.sendAPI.message.quick_reply;

import org.springframework.stereotype.Component;
import xyz.ndlr.model.provider.facebook.sendAPI.message.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class QuickReplyMessageFactory {
    private static final String DEFAULT_IMAGE_PROVIDER_ACTION = "upload";

    public TextMessage generateForProviders(String imageUrl,
                                            Map<String, String> providerToHumanName) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        String payload;
        for (Map.Entry<String, String> provider : providerToHumanName.entrySet()) {
            payload = String.format("%s %s %s", provider.getKey(),
                    DEFAULT_IMAGE_PROVIDER_ACTION, imageUrl);
            quickReply = new QuickReply(QuickReplyContentType.TEXT,
                    provider.getValue(), payload);

            quickReplies.add(quickReply);
        }

        return new TextMessage("Where should this image be sent to ?", quickReplies);
    }
}
