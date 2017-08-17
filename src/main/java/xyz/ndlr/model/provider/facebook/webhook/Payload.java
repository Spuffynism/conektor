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
    private List<Entry> entry;

    public Payload() {
    }

    public Payload(String object, List<Entry> entry) {
        this.object = object;
        this.entry = entry;
    }

    public String tryGetRecipientId() {
        String recipientId = null;
        Entry firstEntry = tryGetFirstEntry();

        if(firstEntry != null)
            recipientId = firstEntry.tryGetMessagingRecipientId();

        return recipientId;
    }

    private Entry tryGetFirstEntry() {
        return entry == null ? null : entry.get(0);
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

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
}