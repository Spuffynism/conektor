package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.parsing.FacebookMessageParser;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.FacebookMessaging;
import com.springmvc.model.provider.facebook.FacebookResponsePayload;

import java.util.List;
import java.util.Map;

public class Dispatcher {
    private List<IProviderResponse> responses;
    private String facebookSenderId;
    private ProviderResponseToFacebookPayloadMapper mapper;

    public Dispatcher(String facebookSenderId) {
        this.facebookSenderId = facebookSenderId;
        this.mapper = new ProviderResponseToFacebookPayloadMapper();
    }

    public void dispatch(List<FacebookMessaging> messagings) throws CannotDispatchException {
        for (FacebookMessaging m : messagings)
            dispatch(m);
    }

    private void dispatch(FacebookMessaging messaging) throws CannotDispatchException {
        if (facebookSenderId == null)
            throw new CannotDispatchException("facebook sender id cannot be null - a message " +
                    "needs a destination");

        String message = messaging.getMessage().getText();

        try {
            FacebookMessageParser parser = new FacebookMessageParser(message);
            dispatchToApp(parser.getAppName(), parser.getArguments());
        } catch (IllegalArgumentException e) {
            throw new CannotDispatchException();
        }
    }

    /**
     * Dispatches the aguments' handling to appName' provider
     * <p>
     * It's ok to suppress the unchecked warning because the dispatchers' dispatch method always
     * returns a list of objects which extend IProviderResponse and we'll only need to call
     * IProviderResponse's methods, not the implementation's when/if we manipulate the responses.
     *
     * @param appName   the app's name
     * @param arguments arguments ex.: -add card -list "list x"
     * @throws IllegalArgumentException if the app name's invalid
     */
    @SuppressWarnings("unchecked")
    private void dispatchToApp(String appName, Map<String, String> arguments)
            throws IllegalArgumentException {
        SupportedProvider provider = SupportedProvider.valueOf(appName.toUpperCase());

        AbstractProviderDispatcher dispatcher = provider.getProviderDispatcher();

        responses = dispatcher.dispatch(arguments);
    }

    public List<FacebookResponsePayload> getFacebookResponsePayloads() {
        return mapper.apply(responses);
    }
}
