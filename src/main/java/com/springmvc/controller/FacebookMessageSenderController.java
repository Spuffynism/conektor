package com.springmvc.controller;

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
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("access_token", pageAccessToken);

        restTemplate.setDefaultUriVariables(uriVariables);
    }

    @Value("${facebook.page_access_token")
    private void setPageAccessToken(String token) {
        pageAccessToken = token;
    }

    public void sendError(FacebookPayload payload, Exception e) {
        String recipientId;
        try {
            recipientId = payload.getEntries().get(0).getMessagings().get(0).getSender
                    ().getId();
        } catch (Exception ignored) {
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
        HttpEntity<FacebookResponsePayload> responsePayloadEntity
                = new HttpEntity<>(payload);

        // Facebook tells us if everything was ok
        ResponseEntity<FacebookResponsePayloadResponse> responsePayloadResponse =
                restTemplate.exchange(requestURI, HttpMethod.POST, responsePayloadEntity,
                        FacebookResponsePayloadResponse.class);

        if (responsePayloadResponse.getStatusCode() == HttpStatus.OK) {
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
