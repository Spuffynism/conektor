package com.springmvc.model.provider.twitter;

import com.springmvc.model.provider.IProviderDispatcher;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Map;

public class TwitterDispatcher implements IProviderDispatcher<TwitterArgument, TwitterResponse> {
    @Override
    public List<TwitterResponse> act(Map<String, String> arguments) throws InvalidArgumentException {
        return null;
    }

    @Override
    public List<TwitterResponse> act(List<TwitterArgument> arguments) throws InvalidArgumentException {
        return null;
    }
}
