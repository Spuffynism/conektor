package xyz.ndlr.model.provider.facebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.ndlr.controller.logging.LoggingRequestInterceptor;
import xyz.ndlr.exception.CannotSendMessageException;
import xyz.ndlr.model.provider.facebook.sendAPI.SendablePayload;

import java.net.URI;

/**
 * Sends messages to facebook
 */
@Component
public class FacebookMessageSender {
    private static final Logger logger = Logger.getLogger(FacebookMessageSender.class);

    private static final String ACCESS_TOKEN = "access_token";
    private static String pageAccessToken;
    //private static final String requestURI = "https://graph.facebook.com/v2.9/me/messages";
    private static final String requestURI = "http://localhost:3000";
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    public FacebookMessageSender() {
        this.asyncRestTemplate = new AsyncRestTemplate();
        initRestTemplate();
    }

    private void initRestTemplate() {
        // set headers
        /*HttpHeaders headers = new HttpHeaders();
        List<MediaType> acceptedHeaders = new ArrayList<>();
        acceptedHeaders.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptedHeaders);*/

        asyncRestTemplate.getInterceptors().add(new LoggingRequestInterceptor());
    }

    @Value("${facebook.page_access_token}")
    private void setPageAccessToken(String token) {
        pageAccessToken = token;
    }

    public void send(SendablePayload payload) throws CannotSendMessageException {
        if (payload.getRecipient() == null)
            throw new CannotSendMessageException("no recipient");

        HttpEntity<SendablePayload> responseEntity = new HttpEntity<>(payload);
        UriComponentsBuilder responseBuilder = UriComponentsBuilder.fromHttpUrl(requestURI)
                .queryParam(ACCESS_TOKEN, pageAccessToken);
        URI responseURI = responseBuilder.build().encode().toUri();

        ListenableFuture<ResponseEntity<Object>> confirmation =
                asyncRestTemplate.postForEntity(responseURI, responseEntity, Object.class);

        confirmation.addCallback(this::logSuccess, this::logFailure);
    }

    private void logSuccess(Object confirmation) {
        String body = null;
        try {
            body = new ObjectMapper().writeValueAsString(confirmation);
        } catch (JsonProcessingException ignored) {
        }

        logger.info("Message has been successfully sent to user\n" + body);
    }

    private void logFailure(Throwable throwable) {
        logger.error("Everything has gone wrong (even telling the user an " +
                "error occured)!", throwable);
    }
}