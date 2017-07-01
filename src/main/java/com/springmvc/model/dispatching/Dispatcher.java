package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.parsing.FacebookMessageParser;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.FacebookMessaging;
import com.springmvc.model.provider.trello.TrelloDispatcher;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Map;

public class Dispatcher {
    private List<IProviderResponse> responses;

    public Dispatcher() {
    }

    public void dispatch(List<FacebookMessaging> messagings) throws CannotDispatchException {
        for (FacebookMessaging m : messagings)
            dispatch(m);
    }

    public void dispatch(FacebookMessaging messaging) throws CannotDispatchException {
        String message = messaging.getMessage();

        try {
            FacebookMessageParser parser = new FacebookMessageParser(message);
            dispatchToApp(parser.getAppName(), parser.getArguments());
        } catch (InvalidArgumentException e) {
            throw new CannotDispatchException();
        }
    }

    private void dispatchToApp(String appName, Map<String, String> arguments)
            throws InvalidArgumentException {
        switch(appName) {
            case "trello":
                responses.addAll(new TrelloDispatcher().act(arguments));
                break;
            case "twitter":
            case "ssh":
            default:
                throw new InvalidArgumentException(new String[]{appName});
        }
    }

    public List<IProviderResponse> getResponses() {
        return responses;
    }
}
