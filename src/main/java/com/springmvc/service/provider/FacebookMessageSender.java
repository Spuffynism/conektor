package com.springmvc.service.provider;

import com.springmvc.exception.CannotDispatchException;
import com.springmvc.exception.UnregisteredAccountException;
import com.springmvc.model.provider.facebook.FacebookPayload;
import com.springmvc.model.provider.facebook.FacebookResponsePayload;
import com.springmvc.model.provider.facebook.FacebookResponsePayloadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacebookMessageSender {
    private static String pageAccessToken;
    private static final String requestURI = "https://graph.facebook.com/v2.9/me/messages";
    private RestTemplate restTemplate;

    public FacebookMessageSender() {
        this.restTemplate = new RestTemplate();
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
            sendErrorMessage(recipientId, e.getMessage(), 0);
        } else {
            sendGenericErrorMessage(recipientId);
        }
    }

    private void sendErrorMessage(String recipientId, String message, int attemptedDeliveries) {
        // set object
        FacebookResponsePayload responsePayload = new FacebookResponsePayload(recipientId, message);
        HttpEntity<FacebookResponsePayload> responsePayloadEntity
                = new HttpEntity<>(responsePayload);

        // set headers
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> acceptedHeaders = new ArrayList<>();
        acceptedHeaders.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptedHeaders);

        // set token
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("access_token", pageAccessToken);

        // Facebook tells us if everything was ok
        ResponseEntity<FacebookResponsePayloadResponse> responsePayloadResponse =
                restTemplate.exchange(requestURI, HttpMethod.POST, responsePayloadEntity,
                        FacebookResponsePayloadResponse.class, uriVariables);

        if (responsePayloadResponse.getStatusCode() == HttpStatus.OK) {
            System.out.println("all good - error message has been successfully sent to user");
        } else if(attemptedDeliveries < 3){
            sendErrorMessage(recipientId, message, ++attemptedDeliveries);
        } else {
            // Give up - everything has gone wrong!
        }
    }

    private void sendGenericErrorMessage(String recipientId) {
        String genericErrorMessage = "An unknown error has occured. Please check your message is " +
                "correctly formatted.";
        sendErrorMessage(recipientId, genericErrorMessage, 0);
    }

    public void sendMessage(FacebookResponsePayload payload) {

    }
}
