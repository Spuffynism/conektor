package xyz.ndlr.infrastructure.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessagingType {
    /**
     * Message is in response to a received message. This includes promotional and
     * non-promotional messages sent inside the 24-hour standard messaging window or under the
     * 24+1 policy. For example, use this tag to respond if a person asks for a reservation
     * confirmation or an status update.
     */
    @JsonProperty("RESPONSE")
    RESPONSE,
    /**
     * Message is being sent proactively and is not in response to a received message. This
     * includes promotional and non-promotional messages sent inside the the 24-hour standard
     * messaging window or under the 24+1 policy.
     */
    @JsonProperty("UPDATE")
    UPDATE,
    /**
     * Message is non-promotional and is being sent outside the 24-hour standard messaging window
     * with a message tag. The message must match the allowed use case for the tag.
     */
    @JsonProperty("MESSAGE_TAG")
    MESSAGE_TAG;

    public static final MessagingType DEFAULT = RESPONSE;
}
