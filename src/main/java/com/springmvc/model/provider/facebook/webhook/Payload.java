package com.springmvc.model.provider.facebook.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
