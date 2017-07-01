package com.springmvc.model.provider.twitter;

import com.springmvc.model.provider.IProviderResponse;

public class TwitterResponse implements IProviderResponse {
    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public int getTripTime() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
