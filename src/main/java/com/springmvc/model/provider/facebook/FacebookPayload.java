package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookPayload {
    private String object;
    private List<FacebookEntry> entries;

    public FacebookPayload() {
    }

    public FacebookPayload(String object, List<FacebookEntry> entries) {
        this.object = object;
        this.entries = entries;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<FacebookEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FacebookEntry> entries) {
        this.entries = entries;
    }
}
