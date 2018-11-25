package xyz.ndlr.domain.provider.facebook.sendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotificationType {
    /**
     * default value for message - emits a sound/vibration and a phone notification
     */
    @JsonProperty("REGULAR")
    REGULAR,
    /**
     * just emits a phone notification
     */
    @JsonProperty("SILENT_PUSH")
    SILENT_PUSH,
    /**
     * does not emit either
     */
    @JsonProperty("NO_PUSH")
    NO_PUSH;

    public static final NotificationType DEFAULT = REGULAR;
}
