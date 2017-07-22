package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.shared.AttachmentType;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.provider.imgur.ImgurDispatcher;

import java.util.List;

public class MediaDispatcher extends AbstractSubDispatcher implements IMessagingDispatcher {

    public MediaDispatcher(List<ProviderResponse> responses) {
        super(responses);
    }

    @Override
    public void dispatch(Messaging messaging) throws CannotDispatchException {
        if (messaging.getMessage().contains(AttachmentType.IMAGE)) {
            AbstractProviderDispatcher dispatcher = new ImgurDispatcher();
            responses = dispatcher.dispatch(messaging);
        }
    }
}
