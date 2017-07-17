package com.springmvc.model.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SenderAction {
    @JsonProperty("typing_on")
    TYPING_ON,
    @JsonProperty("typing_off")
    TYPING_OFF,
    @JsonProperty("mark_seen")
    MARK_SEEN;
}
