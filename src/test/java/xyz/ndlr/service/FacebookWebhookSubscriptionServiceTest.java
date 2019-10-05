package xyz.ndlr.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;
import xyz.ndlr.infrastructure.provider.facebook.Challenge;
import xyz.ndlr.infrastructure.provider.facebook.FacebookVerificationToken;
import xyz.ndlr.infrastructure.provider.facebook.FacebookVerificationTokenFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacebookWebhookSubscriptionServiceTest {

    private static final String VALID_VERIFY_TOKEN_VALUE = "valid token";
    private static final Map<String, String> SOME_REQUEST_PARAMETERS = new HashMap<>();
    private static final Map<String, String> SOME_VALID_REQUEST_PARAMETERS = new HashMap<>();
    private static final FacebookVerificationToken A_FACEBOOK_VERIFICATION_TOKEN;
    private static final FacebookVerificationToken A_VALID_FACEBOOK_VERIFICATION_TOKEN;

    static {
        SOME_REQUEST_PARAMETERS.put("hub.mode", "value 1");
        SOME_REQUEST_PARAMETERS.put("hub.challenge", "value 2");
        SOME_REQUEST_PARAMETERS.put("hub.verify_token", "value 3");

        A_FACEBOOK_VERIFICATION_TOKEN = new FacebookVerificationToken(SOME_REQUEST_PARAMETERS);

        SOME_VALID_REQUEST_PARAMETERS.put("hub.mode", "subscribe");
        SOME_VALID_REQUEST_PARAMETERS.put("hub.challenge", "value 2");
        SOME_VALID_REQUEST_PARAMETERS.put("hub.verify_token", VALID_VERIFY_TOKEN_VALUE);

        A_VALID_FACEBOOK_VERIFICATION_TOKEN =
                new FacebookVerificationToken(SOME_VALID_REQUEST_PARAMETERS);

        ReflectionTestUtils.setField(A_VALID_FACEBOOK_VERIFICATION_TOKEN,
                "verifyToken",
                VALID_VERIFY_TOKEN_VALUE,
                String.class
        );
    }

    @Mock
    FacebookVerificationTokenFactory facebookVerificationTokenFactory;

    @InjectMocks
    FacebookWebhookSubscriptionService facebookWebhookSubscriptionService;

    @Test(expected = InvalidFacebookVerificationTokenException.class)
    public void givenInvalidRequestParameters_whenSubscribing_throwsException()
            throws InvalidFacebookVerificationTokenException {
        when(facebookVerificationTokenFactory.createFromRequestParameters(SOME_REQUEST_PARAMETERS))
                .thenReturn(A_FACEBOOK_VERIFICATION_TOKEN);

        facebookWebhookSubscriptionService.subscribe(SOME_REQUEST_PARAMETERS);
    }

    @Test
    public void givenValidRequestParameters_whenSubscribing_createsTokenFromRequestParameters()
            throws InvalidFacebookVerificationTokenException {
        when(facebookVerificationTokenFactory
                .createFromRequestParameters(SOME_VALID_REQUEST_PARAMETERS))
                .thenReturn(A_VALID_FACEBOOK_VERIFICATION_TOKEN);

        facebookWebhookSubscriptionService.subscribe(SOME_VALID_REQUEST_PARAMETERS);

        verify(facebookVerificationTokenFactory)
                .createFromRequestParameters(SOME_VALID_REQUEST_PARAMETERS);
    }

    @Test
    public void givenValidRequestParameters_whenSubscribing_returnsChallengeOfRightValue()
            throws InvalidFacebookVerificationTokenException {
        when(facebookVerificationTokenFactory
                .createFromRequestParameters(SOME_VALID_REQUEST_PARAMETERS))
                .thenReturn(A_VALID_FACEBOOK_VERIFICATION_TOKEN);

        Challenge challenge =
                facebookWebhookSubscriptionService.subscribe(SOME_VALID_REQUEST_PARAMETERS);

        assertEquals(challenge.getValue(), SOME_VALID_REQUEST_PARAMETERS.get("hub.challenge"));
    }
}