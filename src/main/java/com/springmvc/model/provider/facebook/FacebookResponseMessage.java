package com.springmvc.model.provider.facebook;

public class FacebookResponseMessage {
    private String text;

    public FacebookResponseMessage() {
    }

    public FacebookResponseMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
