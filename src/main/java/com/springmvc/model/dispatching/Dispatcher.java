package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.entity.User;
import com.springmvc.model.parsing.MessageParser;
import com.springmvc.model.parsing.ParsedMessage;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.PipelinedMessage;
import com.springmvc.model.provider.facebook.sendAPI.SendPayload;
import com.springmvc.model.provider.facebook.webhook.Messaging;
import com.springmvc.model.provider.imgur.ImgurDispatcher;

import java.util.List;

public class Dispatcher {
    private static final ProviderDispatcherFactory providerDispatcherFactory
            = new ProviderDispatcherFactory();
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

        dispatchToProvider(messaging);
    }

    /**
     * Dispatches the aguments' handling to command' provider
     *
     * @param messaging     the original raw messaging from the user
     * @throws IllegalArgumentException if the app name's invalid
     */
    private void dispatchToProvider(Messaging messaging)
            throws IllegalArgumentException, CannotDispatchException {
        AbstractProviderDispatcher dispatcher;

        String message = messaging.getMessage().getText();

        ParsedMessage parsedMessage = null;
        if (messaging.getMessage().containsImages()) {
            dispatcher = new ImgurDispatcher();
        } else {
            try {
                MessageParser parser = new MessageParser(message);

                parsedMessage = parser.getParsedMessage();
            } catch (IllegalArgumentException e) {
                throw new CannotDispatchException(e.getMessage());
            }

            dispatcher = providerDispatcherFactory
                    .getFromDestinationProvider(parsedMessage.getCommand());
        }

        PipelinedMessage pipelinedMessage = new PipelinedMessage(messaging, parsedMessage);

        responses = dispatcher.dispatch(pipelinedMessage);
    }

    public List<SendPayload> collectFacebookResponsePayloads() {
        return mapper.apply(this.responses, facebookSenderId);
    }
}
