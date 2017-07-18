package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.entity.User;
import com.springmvc.model.parsing.MessageParser;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.sendAPI.Payload;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.provider.imgur.ImgurDispatcher;
import com.springmvc.model.provider.trello.TrelloDispatcher;

import java.util.List;
import java.util.Map;

public class Dispatcher {
    private List<ProviderResponse> responses;
    private User user;
    private String facebookSenderId;
    private ProviderResponseToFacebookMessagePayloadMapper mapper;

    public Dispatcher(User user, String facebookSenderId) {
        this.user = user;
        this.facebookSenderId = facebookSenderId;
        this.mapper = new ProviderResponseToFacebookMessagePayloadMapper();
    }

    public void dispatch(List<Messaging> messagings) throws CannotDispatchException {
        for (Messaging m : messagings)
            dispatch(m);
    }

    private void dispatch(Messaging messaging) throws CannotDispatchException {
        if (user == null)
            throw new CannotDispatchException("user cannot be null - a message needs a " +
                    "destination");

        String message = messaging.getMessage().getText();

        try {
            MessageParser parser = new MessageParser(message);
            dispatchToProvider(parser.getCommand(), parser.getArguments());
        } catch (IllegalArgumentException e) {
            throw new CannotDispatchException(e.getMessage());
        }
    }

    /**
     * Dispatches the aguments' handling to command' provider
     * <p>
     * It's ok to suppress the unchecked warning because the dispatchers' dispatch method always
     * returns a list of objects which implement IProviderResponse and we'll only need to call
     * IProviderResponse's methods, not the implementation's when we manipulate the responses.
     *
     * @param command   the app's name
     * @param arguments arguments ex.: -add card -list "list x"
     * @throws IllegalArgumentException if the app name's invalid
     */
    @SuppressWarnings("unchecked")
    private void dispatchToProvider(String command, Map<String, String> arguments)
            throws IllegalArgumentException {
        AbstractProviderDispatcher dispatcher = null;

        try {
            switch (SupportedProvider.valueOf(command.toUpperCase())) {
                case FACEBOOK:
                    dispatcher = null;
                    break;
                case IMGUR:
                    dispatcher = new ImgurDispatcher();
                    break;
                case SSH:
                    dispatcher = null;
                    break;
                case TRELLO:
                    dispatcher = new TrelloDispatcher();
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("unknown provider");
        }

        if (dispatcher == null)
            throw new IllegalArgumentException("unknown command led to unknown provider");

        responses = dispatcher.dispatch(arguments);
    }

    public List<Payload> collectFacebookResponsePayloads() {
        return mapper.apply(this.responses, facebookSenderId);
    }
}
