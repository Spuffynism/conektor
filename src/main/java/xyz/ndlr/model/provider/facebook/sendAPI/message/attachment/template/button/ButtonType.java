package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ButtonType {
    // Not implemented
    @JsonProperty("payment")
    BUY,
    // Not implemented
    @JsonProperty("phone_number")
    CALL,
    // Not implemented
    @JsonProperty("game_play")
    GAME_PLAY,
    @JsonProperty("account_link")
    LOG_IN,
    @JsonProperty("account_unlink")
    LOG_OUT,
    @JsonProperty("postback")
    POSTBACK,
    // Not implemented
    @JsonProperty("element_share")
    SHARE,
    @JsonProperty("web_url")
    URL;
}
