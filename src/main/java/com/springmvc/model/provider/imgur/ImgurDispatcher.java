package com.springmvc.model.provider.imgur;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.facebook.webhook.Messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImgurDispatcher extends AbstractProviderDispatcher<Messaging> {
    private static final ImgurService imgurService = new ImgurService();

    @Override
    public List<ProviderResponse> dispatch(Messaging messaging) throws
            IllegalArgumentException {
        List<ProviderResponse> responses = new ArrayList<>();

        responses.add(imgurService.upload(messaging));
        /*switch (getFirstAction(pipelinedMessage.getParsedMessage().getArguments(),
                ImgurAction.class)) {
            case UPLOAD:
                responses.add(imgurService.upload(pipelinedMessage));
                break;
            case DELETE:
                responses.add(imgurService.delete(pipelinedMessage));
                break;
            default:
                break;
        }*/

        if (responses.isEmpty())
            throw new IllegalArgumentException("no response from provider");

        return responses;
    }
}
