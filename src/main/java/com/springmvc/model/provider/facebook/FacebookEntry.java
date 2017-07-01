package com.springmvc.model.provider.facebook;

import java.util.List;

public class FacebookEntry {
    private String id;
    private int time;
    private List<FacebookMessaging> messagings;

    public FacebookEntry() {
    }

    public FacebookEntry(String id, int time, List<FacebookMessaging> messagings) {
        this.id = id;
        this.time = time;
        this.messagings = messagings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<FacebookMessaging> getMessagings() {
        return messagings;
    }

    public void setMessagings(List<FacebookMessaging> messagings) {
        this.messagings = messagings;
    }
}
