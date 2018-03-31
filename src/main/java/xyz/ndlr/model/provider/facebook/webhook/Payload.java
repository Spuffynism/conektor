package xyz.ndlr.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Primary payload send by Facebook when a user sends a message
 */
public class Payload {
    private static final String PAGE_OBJECT = "page";
    /**
     * Value will be page
     */
    @JsonProperty("object")
    private String object;
    /**
     * Array containing event data
     */
    @JsonProperty("entry")
    private List<Entry> entries;

    public Payload() {
    }

    public String tryGetSenderId() {
        String senderId = null;
        Entry firstEntry = tryGetFirstEntry();

        if (firstEntry != null)
            senderId = firstEntry.tryGetMessagingSenderId();

        return senderId;
    }

    private Entry tryGetFirstEntry() {
        return entries == null ? null : entries.get(0);
    }

    public boolean isPage() {
        return object != null && object.equalsIgnoreCase(PAGE_OBJECT);
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
