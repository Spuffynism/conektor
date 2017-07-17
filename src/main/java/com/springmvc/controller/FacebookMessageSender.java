package com.springmvc.controller;

import com.springmvc.controller.logging.LoggingRequestInterceptor;
import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.CannotSendMessageException;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.provider.facebook.sendAPI.PayloadFactory;
import com.springmvc.model.provider.facebook.webhook.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Sends messages to facebook
 */
@Component
public class FacebookMessageSender {
    private static final String ACCESS_TOKEN = "access_token";
    private static String pageAccessToken;
    private static final String requestURI = "https://graph.facebook.com/v2.9/me/messages";
    private RestTemplate restTemplate;
    private PayloadFactory payloadFactory;

    // TODO make payloadFactory dependency-injectable
    public FacebookMessageSender() {
        this.restTemplate = new RestTemplate();
        this.payloadFactory = new PayloadFactory();
        initRestTemplate();
    }

    private void initRestTemplate() {
        // set headers
        /*HttpHeaders headers = new HttpHeaders();
        List<MediaType> acceptedHeaders = new ArrayList<>();
        acceptedHeaders.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptedHeaders);*/

        restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
    }

    @Value("${facebook.page_access_token}")
    private void setPageAccessToken(String token) {
        pageAccessToken = token;
    }

    public void send(String recipientId, String message) throws CannotSendMessageException {
        send(payloadFactory.getPayload(recipientId, message));
    }

    public void send(List<com.springmvc.model.provider.facebook.sendAPI.Payload> payloads) throws CannotSendMessageException {
        for (com.springmvc.model.provider.facebook.sendAPI.Payload p : payloads)
            send(p);
    }

    private void send(com.springmvc.model.provider.facebook.sendAPI.Payload payload) throws CannotSendMessageException {
       /* if (payload.getMessage() == null)
            payload.setMessage("no message");*/

        if (payload.getRecipient() == null)
            throw new CannotSendMessageException("no recipient");

        // TODO Check with webhook reference
        HttpEntity<com.springmvc.model.provider.facebook.sendAPI.Payload> responseEntity = new HttpEntity<>(payload);
        UriComponentsBuilder responseBuilder = UriComponentsBuilder.fromHttpUrl(requestURI)
                .queryParam(ACCESS_TOKEN, pageAccessToken);
        URI responseURI = responseBuilder.build().encode().toUri();

        ResponseEntity<Object> confirmation = null;
        try {
            confirmation = restTemplate.exchange(responseURI, HttpMethod.POST, responseEntity,
                    Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (confirmation != null && confirmation.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message has been successfully sent to user");
        } else {
            System.out.println("Everything has gone wrong (even telling the user an " +
                    "error occured)!");
        }
    }

    void sendError(Payload payload, Exception exception) {
        String recipientId;
        try {
            recipientId = payload.getEntry().get(0).getMessaging().get(0).getSender().getId();
        } catch (Exception ignored) {
            System.out.println("No recipient!");
            // Without a recipient, we can't send a message!
            return;
        }

        try {
            if (exception instanceof CannotDispatchException ||
                    exception instanceof UnregisteredAccountException) {
                send(recipientId, exception.getMessage());
            } else {
                sendGenericErrorMessage(recipientId);
            }
        } catch (Exception e) {
            System.out.println("Could not send error message: ");
            e.printStackTrace();
        }
    }

    private void sendGenericErrorMessage(String recipientId) throws CannotSendMessageException {
        String genericErrorMessage = "An unknown error has occured. Please check your message is " +
                "correctly formatted.";
        send(recipientId, genericErrorMessage);
    }
}
