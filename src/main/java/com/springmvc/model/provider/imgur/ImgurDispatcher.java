package com.springmvc.model.provider.imgur;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImgurDispatcher extends AbstractProviderDispatcher {
    private static final ImgurService imgurService = new ImgurService();

    @Override
    public List<ProviderResponse> dispatch(Map<String, String> arguments) throws
            IllegalArgumentException {
        List<ProviderResponse> responses = new ArrayList<>();

        switch (getFirstAction(arguments, ImgurAction.class)) {
            case UPLOAD:
                responses.add(imgurService.upload(arguments));
                break;
            case DELETE:
                responses.add(imgurService.delete(arguments));
                break;
            default:
                break;
        }

        if (responses.isEmpty())
            throw new IllegalArgumentException("no response from provider");

        return responses;
    }
}
