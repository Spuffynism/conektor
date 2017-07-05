package com.springmvc.model.provider.twitter;

import com.springmvc.model.provider.IProviderAction;

public enum TwitterAction implements IProviderAction {
    TWEET("tweet");

    private String value;

    TwitterAction(String value) {
        this.value = value;
    }
}
