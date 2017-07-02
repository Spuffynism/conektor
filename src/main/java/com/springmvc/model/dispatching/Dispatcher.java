package com.springmvc.model.dispatching;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.model.parsing.FacebookMessageParser;
import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.model.provider.IProviderResponse;
import com.springmvc.model.provider.facebook.FacebookMessaging;
import com.springmvc.model.provider.trello.TrelloDispatcher;
import com.springmvc.model.provider.twitter.TwitterDispatcher;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Map;

public class Dispatcher {
    private List<IProviderResponse> responses;
    private String facebookSenderId;

    public Dispatcher(String facebookSenderId) {
        this.facebookSenderId = facebookSenderId;
    }

    public void dispatch(List<FacebookMessaging> messagings) throws CannotDispatchException {
        for (FacebookMessaging m : messagings)
            dispatch(m);
    }

    public void dispatch(FacebookMessaging messaging) throws CannotDispatchException {
        if (facebookSenderId == null)
            throw new CannotDispatchException("facebook sender id cannot be null - a message " +
                    "needs a destination");

        String message = messaging.getMessage().getText();

        try {
            FacebookMessageParser parser = new FacebookMessageParser(message);
            dispatchToApp(parser.getAppName(), parser.getArguments());
        } catch (InvalidArgumentException e) {
            throw new CannotDispatchException();
        }
    }

    private void dispatchToApp(String appName, Map<String, String> arguments)
            throws InvalidArgumentException {
        AbstractProviderDispatcher dispatcher;
        switch (SupportedProvider.valueOf(appName.toLowerCase())) {
            case TRELLO:
                System.out.println("dispatching to trello...");
                dispatcher = new TrelloDispatcher();
                break;
            case TWITTER:
                System.out.println("dispatching to twitter...");
                dispatcher = new TwitterDispatcher();
                break;
            case SSH:
            default:
                System.out.println("Invalid app name...");
                throw new InvalidArgumentException(new String[]{appName});
        }

        responses.addAll(dispatcher.dispatch(arguments));
    }


    public List<IProviderResponse> getResponses() {
        return responses;
    }

    public String getFacebookSenderId() {
        return facebookSenderId;
    }

    public void setFacebookSenderId(String facebookSenderId) {
        this.facebookSenderId = facebookSenderId;
    }
}
