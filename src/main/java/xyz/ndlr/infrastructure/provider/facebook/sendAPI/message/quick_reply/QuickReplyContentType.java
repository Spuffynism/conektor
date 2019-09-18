package xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.quick_reply;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum QuickReplyContentType {
    /**
     * Sends a text button
     */
    @JsonProperty("text")
    TEXT,
    /**
     * Sends a button to collect the recipient's location
     */
    @JsonProperty("location")
    LOCATION,
    /**
     * Sends a button allowing recipient to send the phone number associated with their account
     */
    @JsonProperty("user_phone_number")
    USER_PHONE_NUMBER,
    /**
     * Sends a button allowing recipient to send the email associated with their account.
     */
    @JsonProperty("user_email")
    USER_EMAIL;
}
