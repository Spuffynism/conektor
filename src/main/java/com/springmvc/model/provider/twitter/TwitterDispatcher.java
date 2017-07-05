package com.springmvc.model.provider.twitter;

import com.springmvc.model.provider.AbstractProviderDispatcher;
import com.springmvc.service.provider.TwitterService;

import java.util.ArrayList;
import java.util.List;

import static com.springmvc.model.provider.twitter.TwitterAction.TWEET;

public class TwitterDispatcher extends AbstractProviderDispatcher<TwitterArgument,
        TwitterResponse> {
    private static TwitterService twitterService = new TwitterService();

    public TwitterDispatcher() {
        super(TwitterArgument.class);
    }

    public List<TwitterResponse> dispatch(List<TwitterArgument> arguments) throws
            IllegalArgumentException {
        List<TwitterResponse> responses = new ArrayList<>();

        String firstAction = arguments.get(0).getAction();

        switch(TwitterAction.valueOf(firstAction.toUpperCase())) {
            case TWEET:
                responses.add(twitterService.tweet(arguments));
                break;
            default:
                break;
        }

        if(responses.isEmpty())
            throw new IllegalArgumentException("no responses");

        return responses;
    }
}
