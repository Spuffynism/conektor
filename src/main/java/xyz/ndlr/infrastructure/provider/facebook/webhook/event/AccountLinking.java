package xyz.ndlr.infrastructure.provider.facebook.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://developers.facebook.com/docs/messenger-platform/reference/webhook-events
 * /messaging_account_linking
 */
public class AccountLinking {
    /**
     * indicates whether the user linked or unlinked their account
     */
    @JsonProperty("status")
    private Status status;
    /**
     * Value of pass-through authorization_code provided in the Account Linking flow
     * <p>
     * Note: authorization_code is only available when status is linked
     */
    @JsonProperty("authorization_code")
    private String authorizationCode;

    enum Status {
        @JsonProperty("linked")
        LINKED,
        @JsonProperty("unlinked")
        UNLINKED;
    }
}
