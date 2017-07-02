package com.springmvc.model.provider.twitter;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;

public class TwitterDispatcher extends AbstractProviderDispatcher<TwitterArgument,
        TwitterResponse> {

    public TwitterDispatcher() {
        super(TwitterArgument.class);
    }

    public List<TwitterResponse> act(List<TwitterArgument> arguments) throws
            InvalidArgumentException {
        return null;
    }

    @Override
    protected List<TwitterResponse> dispatch(List<TwitterArgument> arguments) throws
            InvalidArgumentException {
        return null;
    }
}
