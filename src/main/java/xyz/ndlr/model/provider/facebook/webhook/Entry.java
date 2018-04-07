package xyz.ndlr.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Entry {
    /**
     * ID of page
     */
    @JsonProperty("id")
    private String pageId;
    /**
     * Time of update (epoch time in milliseconds)
     */
    @JsonProperty("time")
    private long time;
    /**
     * Array containing objects related to messagings.
     * Will always contain only one object.
     * see: https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/#entry
     */
    @JsonProperty("messaging")
    private List<Messaging> messagings;

    public Entry() {
    }

    String tryGetMessagingSenderId() {
        String senderId = null;
        Messaging firstMessaging = getMessaging();

        if (firstMessaging != null)
            senderId = firstMessaging.tryGetSenderId();

        return senderId;
    }

    /**
     * messagings will always only contain one messaging.
     * see: https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/#entry
     * @return the only entry in the list
     */
    public Messaging getMessaging() {
        return messagings.get(0);
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

    public List<Messaging> getMessagings() {
        return messagings;
    }

    public void setMessagings(List<Messaging> messagings) {
        this.messagings = messagings;
    }
}
