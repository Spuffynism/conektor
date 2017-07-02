package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookPayload {
    private String object;
    private List<FacebookEntry> entry;

    public FacebookPayload() {
    }

    public FacebookPayload(String object, List<FacebookEntry> entry) {
        this.object = object;
        this.entry = entry;
    }

    public boolean isPage() {
        return object != null && object.equalsIgnoreCase("page");
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<FacebookEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<FacebookEntry> entry) {
        this.entry = entry;
    }
}
