package com.springmvc.model.dispatching;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.imgur.ImgurDispatcher;
import com.springmvc.model.provider.trello.TrelloDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//TODO https://stackoverflow.com/a/39361500/5709703
class ProviderDispatcherFactory {
    private final ImgurDispatcher imgurDispatcher;
    private final TrelloDispatcher trelloDispatcher;

    @Autowired
    public ProviderDispatcherFactory(ImgurDispatcher imgurDispatcher,
                                     TrelloDispatcher trelloDispatcher) {
        this.imgurDispatcher = imgurDispatcher;
        this.trelloDispatcher = trelloDispatcher;
    }

    AbstractProviderDispatcher getFromDestinationProvider(String destinationProvider)
            throws IllegalArgumentException {
        SupportedProvider supportedProvider = SupportedProvider.tryGetProvider(destinationProvider);
        return getFromDestinationProvider(supportedProvider);
    }

    AbstractProviderDispatcher getFromDestinationProvider(
            SupportedProvider supportedProvider) throws IllegalArgumentException {
        AbstractProviderDispatcher dispatcher;

        switch (supportedProvider) {
            case FACEBOOK:
                throw new IllegalArgumentException("unimplemented provider");
            case IMGUR:
                dispatcher = imgurDispatcher;
                break;
            case SSH:
                throw new IllegalArgumentException("unimplemented provider");
            case TRELLO:
                dispatcher = trelloDispatcher;
                break;
            default:
                throw new IllegalArgumentException("unimplemented provider");
        }

        return dispatcher;
    }
}
