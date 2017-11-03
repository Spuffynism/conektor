package xyz.ndlr.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Entry {
    /**
     * Page ID of page
     */
    @JsonProperty("id")
    private String pageId;
    /**
     * Time of update (epoch time in milliseconds)
     */
    @JsonProperty("time")
    private long time;
    /**
     * Array containing objects related to messaging
     */
    @JsonProperty("messaging")
    private List<Messaging> messaging;

    public Entry() {
    }

    public Entry(String pageId, int time, List<Messaging> messaging) {
        this.pageId = pageId;
        this.time = time;
        this.messaging = messaging;
    }

    String tryGetMessagingSenderId() {
        String senderId = null;
        Messaging firstMessaging = tryGetFirstMessaging();

        if (firstMessaging != null)
            senderId = firstMessaging.tryGetSenderId();

        return senderId;
    }

    private Messaging tryGetFirstMessaging() {
        return messaging.get(0);
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Messaging> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }
}
