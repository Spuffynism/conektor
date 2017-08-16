package xyz.ndlr.model.dispatching;

import xyz.ndlr.model.provider.AbstractProviderDispatcher;
import xyz.ndlr.model.provider.imgur.ImgurDispatcher;
import xyz.ndlr.model.provider.trello.TrelloDispatcher;
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

    <T extends IMessage> AbstractProviderDispatcher<T> getFromDestinationProvider(String destinationProvider)
            throws IllegalArgumentException {
        SupportedProvider supportedProvider = SupportedProvider.tryGetProvider(destinationProvider);

        return getFromDestinationProvider(supportedProvider);
    }

    <T extends IMessage> AbstractProviderDispatcher<T> getFromDestinationProvider(
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

        //TODO This doesn't assure type safety...
        return (AbstractProviderDispatcher<T>) dispatcher;
    }
}
