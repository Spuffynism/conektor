package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ButtonType {
    @JsonProperty("element_share")
    ELEMENT_SHARE,
    @JsonProperty("postback")
    POSTBACK,
    @JsonProperty("web_url")
    WEB_URL;
}
