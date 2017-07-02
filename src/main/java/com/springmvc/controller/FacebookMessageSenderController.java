package com.springmvc.controller;

import com.springmvc.controller.logging.LoggingRequestInterceptor;
import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.provider.facebook.FacebookPayload;
import com.springmvc.model.provider.facebook.FacebookResponsePayload;
import com.springmvc.model.provider.facebook.FacebookResponsePayloadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Sends messages to facebook
 */
@Component
public class FacebookMessageSenderController {
    private static String pageAccessToken;
    private static final String requestURI = "https://graph.facebook.com/v2.9/me/messages";
    private RestTemplate restTemplate;

    public FacebookMessageSenderController() {
        this.restTemplate = new RestTemplate();

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

    void sendError(FacebookPayload payload, Exception e) {
        String recipientId;
        try {
            recipientId = payload.getEntry().get(0).getMessaging().get(0).getSender().getId();
        } catch (Exception ignored) {
            System.out.println("No recipient!");
            // Without a recipient, we can't send a message!
            return;
        }

        if (e instanceof CannotDispatchException || e instanceof UnregisteredAccountException) {
            send(recipientId, e.getMessage());
        } else {
            sendGenericErrorMessage(recipientId);
        }
    }

    public void send(String recipientId, String message) {
        send(new FacebookResponsePayload(recipientId, message));
    }

    public void send(List<FacebookResponsePayload> payloads) {
        for (FacebookResponsePayload p : payloads)
            send(p);
    }

    private void send(FacebookResponsePayload payload) {
        if (payload.getMessage().getText() == null)
            payload.setMessage("no message");

        HttpEntity<FacebookResponsePayload> responsePayloadEntity
                = new HttpEntity<>(payload);

        // Facebook tells us if everything was ok
        ResponseEntity<FacebookResponsePayloadResponse> responsePayloadResponse = null;
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI)
                    .queryParam("access_token", pageAccessToken);
            responsePayloadResponse
                    = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                    responsePayloadEntity,
                    FacebookResponsePayloadResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (responsePayloadResponse != null && responsePayloadResponse.getStatusCode() ==
                HttpStatus.OK) {
            System.out.println("All good - message has been successfully sent to user");
        } else {
            System.out.println("Give up - everything has gone wrong!");
        }
    }

    private void sendGenericErrorMessage(String recipientId) {
        String genericErrorMessage = "An unknown error has occured. Please check your message is " +
                "correctly formatted.";
        send(recipientId, genericErrorMessage);
    }
}
