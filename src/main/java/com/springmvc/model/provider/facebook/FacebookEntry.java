package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookEntry {
    private String id;
    private long time;
    private List<FacebookMessaging> messaging;

    public FacebookEntry() {
    }

    public FacebookEntry(String id, int time, List<FacebookMessaging> messaging) {
        this.id = id;
        this.time = time;
        this.messaging = messaging;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<FacebookMessaging> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<FacebookMessaging> messaging) {
        this.messaging = messaging;
    }
}
