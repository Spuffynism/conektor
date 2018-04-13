package xyz.ndlr.model.provider.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.ndlr.exception.CannotSendMessageException;
import xyz.ndlr.model.provider.facebook.sendAPI.SendablePayload;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Sends messages to facebook
 */
@Component
public class FacebookMessageSender {
    private static final Logger logger = Logger.getLogger(FacebookMessageSender.class);

    private static final String ACCESS_TOKEN = "access_token";
    private static String pageAccessToken;
    private static String requestURI;
    private final AsyncRestTemplate asyncRestTemplate;

    @Autowired
    public FacebookMessageSender() {
        this.asyncRestTemplate = new AsyncRestTemplate();
        initRestTemplate();
    }

    private void initRestTemplate() {
        asyncRestTemplate.getInterceptors().add(new RequestLoggerInterceptor());
    }

    @Value("${facebook.page_access_token}")
    private void setPageAccessToken(String token) {
        pageAccessToken = token;
    }

    @Value("${facebook.message_sender_request_uri}")
    private void setRequestURI(String requestURI) {
        FacebookMessageSender.requestURI = requestURI;
    }

    public void send(SendablePayload payload) throws CannotSendMessageException {
        if (payload.getRecipient() == null)
            throw new CannotSendMessageException("no recipient");

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> acceptedHeaders = Collections.singletonList(MediaType
                .APPLICATION_JSON_UTF8);
        headers.setAccept(acceptedHeaders);

        HttpEntity<SendablePayload> responseEntity = new HttpEntity<>(payload, headers);
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

    /**
     * TODO: Implement enum for error code + error handling with appropriate messages
     *
     * @see
     * <a href="https://developers.facebook.com/docs/messenger-platform/reference/send-api/error-codes"/>
     */
    private class ErrorResponse {
        @JsonProperty("error")
        private Error error;

        private class Error {
            @JsonProperty("message")
            String message;
            @JsonProperty("type")
            String type;
            @JsonProperty("code")
            int code;
            @JsonProperty("error_subcode")
            int subCode;
            @JsonProperty("fbtrace_id")
            String facebookTraceID;
        }
    }
}
