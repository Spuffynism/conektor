package xyz.ndlr.model.provider.facebook;

import xyz.ndlr.model.provider.facebook.webhook.Entry;
import xyz.ndlr.model.provider.facebook.webhook.Messaging;
import xyz.ndlr.model.provider.facebook.webhook.Payload;
import xyz.ndlr.model.provider.facebook.webhook.Sender;

import java.util.List;
import java.util.stream.Collectors;

public class FacebookMessageFacade {
    private Entry entry;

    private FacebookMessageFacade(Entry entry) {
        this.entry = entry;
    }

    public Sender getSender() {
        return getMessaging().getSender();
    }

    public Messaging getMessaging() {
        return entry.getMessaging();
    }

    public static List<FacebookMessageFacade> fromPayload(Payload payload) throws
            IllegalArgumentException {
        List<FacebookMessageFacade> messageFacades = withIndividualEntries(payload);

        for (FacebookMessageFacade messageFacade : messageFacades) {
            if (messageFacade.getSender() == null || messageFacade.getSender().getId() == null)
                throw new IllegalArgumentException("no sender");

            if (messageFacade.getMessaging() == null)
                throw new IllegalArgumentException("no message");
        }

        return messageFacades;
    }

    /**
     * Builds FacebookMessageFacades which each contain a singular entry.
     *
     * @param payload the facebook entry which contains a list of entries
     * @return message facades with one entry each
     */
    private static List<FacebookMessageFacade> withIndividualEntries(Payload payload) {
        return payload.getEntries().stream()
                .map(FacebookMessageFacade::new)
                .collect(Collectors.toList());
    }
}
