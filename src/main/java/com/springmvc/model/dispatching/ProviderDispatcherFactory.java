package com.springmvc.model.dispatching;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.imgur.ImgurDispatcher;
import com.springmvc.model.provider.trello.TrelloDispatcher;

class ProviderDispatcherFactory {

    AbstractProviderDispatcher getFromDestinationProvider(String destinationProvider)
            throws IllegalArgumentException {
        SupportedProvider provider;

        try {
            provider = SupportedProvider.valueOf(destinationProvider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("unknown provider");
        }

        return getFromDestinationProvider(provider);
    }

    private AbstractProviderDispatcher getFromDestinationProvider(
            SupportedProvider supportedProvider) throws IllegalArgumentException {
        AbstractProviderDispatcher dispatcher = null;

        switch (supportedProvider) {
            case FACEBOOK:
                throw new IllegalArgumentException("unimplemented provider");
            case IMGUR:
                dispatcher = new ImgurDispatcher();
                break;
            case SSH:
                throw new IllegalArgumentException("unimplemented provider");
            case TRELLO:
                dispatcher = new TrelloDispatcher();
                break;
        }

        return dispatcher;
    }
}
