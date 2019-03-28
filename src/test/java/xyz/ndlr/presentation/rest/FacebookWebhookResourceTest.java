package xyz.ndlr.presentation.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.ndlr.domain.FacebookMessageConsumer;
import xyz.ndlr.domain.exception.InvalidFacebookVerificationTokenException;
import xyz.ndlr.domain.provider.facebook.webhook.Payload;
import xyz.ndlr.service.FacebookWebhookService;
import xyz.ndlr.service.FacebookWebhookSubscribtionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacebookWebhookResourceTest {

    private static final Map<String, String> SOME_REQUEST_PARAMS = new HashMap<>();
    private static final Payload SOME_PAYLOAD = new Payload();

    @Mock
    FacebookMessageConsumer facebookMessageConsumer;

    @Mock
    FacebookWebhookService facebookWebhookService;

    @Mock
    FacebookWebhookSubscribtionService facebookWebhookSubscribtionService;

    @InjectMocks
    FacebookWebhookResource facebookWebhookResource;

    @Test
    public void initially_startsConsuming(){
        verify(facebookMessageConsumer).startConsuming();
    }

    @Test
    public void whenSubscribing_subscribes() throws InvalidFacebookVerificationTokenException {
        facebookWebhookResource.subscribe(SOME_REQUEST_PARAMS);
    }

    @Test
    public void whenSubscribing_includesChallengeInResponse()
            throws InvalidFacebookVerificationTokenException {
        String expectedChallenge = "expected challenge";

        when(facebookWebhookSubscribtionService.subscribe(SOME_REQUEST_PARAMS))
                .thenReturn(expectedChallenge);
        ResponseEntity<String> response = facebookWebhookResource.subscribe(SOME_REQUEST_PARAMS);

        assertEquals(expectedChallenge, response.getBody());
    }

    @Test
    public void givenValidToken_whenSubscribing_producesOKResponse()
            throws InvalidFacebookVerificationTokenException {
        ResponseEntity<String> response = facebookWebhookResource.subscribe(SOME_REQUEST_PARAMS);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test(expected = InvalidFacebookVerificationTokenException.class)
    public void givenInvalidToken_whenSubscribing_throwsException()
            throws InvalidFacebookVerificationTokenException {
        doThrow(InvalidFacebookVerificationTokenException.class)
                .when(facebookWebhookSubscribtionService)
                .subscribe(SOME_REQUEST_PARAMS);

        facebookWebhookResource.subscribe(SOME_REQUEST_PARAMS);
    }

    @Test
    public void whenReceivingMessage_processesPayload() {
        facebookWebhookResource.receiveMessage(SOME_PAYLOAD);

        verify(facebookWebhookService).processPayload(SOME_PAYLOAD);
    }

    @Test
    public void whenReceivingMessage_producesOKResponse() {
        ResponseEntity<String> response = facebookWebhookResource.receiveMessage(SOME_PAYLOAD);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}